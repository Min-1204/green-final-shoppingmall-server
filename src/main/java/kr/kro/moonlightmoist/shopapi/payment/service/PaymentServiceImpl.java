package kr.kro.moonlightmoist.shopapi.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import kr.kro.moonlightmoist.shopapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private IamportClient iamportClient;

    private final OrderService orderService;

    @Value("${portone.rest-api-key}")
    private String restApiKey;

    @Value("${portone.rest-api-secret}")
    private String restApiSecret;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
        log.info("PortOne IamportClient 초기화 완료.");
    }

    @Override
    @Transactional
    public void verifyPaymentAndCompleteOrder(String impUid, String merchantUid) throws Exception {
        // A. PortOne에 실제 결제 정보 요청
        // 이 메서드가 내부적으로 액세스 토큰을 발급(인증)하고 결제 정보를 조회합니다.
        IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);
        Payment payment = paymentResponse.getResponse();

        if(payment == null) {
            throw new IllegalArgumentException("결제 정보를 찾을 수 없습니다. imp_uid: " + impUid);
        }

        // B. 쇼핑몰 DB의 주문 정보 조회
        BigDecimal expectedAmount = orderService.getExpectedAmount(merchantUid);

        // C. 금액 검증(위변조 방지)
        if(payment.getAmount().compareTo(expectedAmount) != 0) {
            log.error("결제 금액 불일치. DB 금액: {}, 실제 결제 금액: {}", expectedAmount, payment.getAmount());

            try{
                CancelData cancelData = new CancelData(impUid,true); // true = 전액취소
                cancelData.setReason("결제 금액 불일치로 인한 자동 취소"); // 취소 사유 (필수)

                //보안상 금액 불일치 시, 즉시 결제 취소 API를 호출해야 합니다.
                IamportResponse<Payment> cancelResponse = iamportClient.cancelPaymentByImpUid(cancelData);

                if(cancelResponse.getCode() == 0) {
                    log.info("결제 취소 완료: {}", cancelResponse.getResponse().getStatus());
                } else {
                    log.error("결제 취소 실패. 응답코드: {}, 메시지: {}",
                            cancelResponse.getCode(), cancelResponse.getMessage());
                    // Todo: 관리자 알림 필요
                }

            } catch (IamportResponseException | IOException e) {
                log.error("결제 취소 API 호출 실패. imp_uid: {}, 에러: {}", impUid, e.getMessage());
                //todo : 관리자 알림 필요
            }

            throw new IllegalStateException("결제 금액이 일치하지 않습니다.");
        }

        // D. 최종 상태 확인
        if (!"paid".equals(payment.getStatus())) {
            throw new IllegalStateException("결제 상태가 'paid'가 아닙니다. (status: " + payment.getStatus() + ")");
        }

        // E. DB 주문 상태 업데이트 (최종 완료)
        orderService.completeOrder(merchantUid);

        log.info("결제 검증 및 주문 완료. 주문번호: {}, imp_uid: {}", merchantUid, impUid);
    }
}
