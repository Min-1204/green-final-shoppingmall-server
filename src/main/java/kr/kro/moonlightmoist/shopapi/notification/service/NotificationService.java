package kr.kro.moonlightmoist.shopapi.notification.service;

import kr.kro.moonlightmoist.shopapi.common.config.CoolSmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final DefaultMessageService messageService;
    private final CoolSmsConfig coolSmsConfig;

    public void sendSmsMessage(String toNumber, String content) {
        try {
            Message message = new Message();
            message.setFrom(coolSmsConfig.getSender());
            message.setTo(toNumber);
            message.setText(content);

            MultipleDetailMessageSentResponse result = messageService.send(message);

            log.info("발송 요청 후 응답 : {}", result);

        } catch (NurigoMessageNotReceivedException e) {
            log.info("수신자에게 발송 실패 : {}", e.getFailedMessageList());
            throw new RuntimeException(e);
        } catch (NurigoEmptyResponseException e) {
            log.info("CoolSms 로부터 응답 없음 : {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (NurigoUnknownException e) {
            log.info("메시지 발송 요청 중 예기치 않은 오류 발생 : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void sendBatchSmsMessage(List<String> toNumbers, String content) {
        List<Message> messages = new ArrayList<>();

        for (String number : toNumbers) {
            Message message = new Message();
            message.setFrom(coolSmsConfig.getSender());
            message.setTo(number);
            message.setText(content);

            messages.add(message);
        }

        try {
            messageService.send(messages);
        } catch (NurigoMessageNotReceivedException e) {
            log.info("일부 수신자에게 발송 실패 : {}", e.getFailedMessageList());
            throw new RuntimeException(e);
        } catch (NurigoEmptyResponseException e) {
            log.info("CoolSms 로부터 응답 없음 : {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (NurigoUnknownException e) {
            log.info("메시지 발송 요청 중 예기치 않은 오류 발생 : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
