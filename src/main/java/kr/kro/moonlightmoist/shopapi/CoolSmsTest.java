package kr.kro.moonlightmoist.shopapi;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

public class CoolSmsTest {
    public static void main(String[] args) {
        // 1. CoolSMS 계정 정보 (여기에 본인 정보 입력!)
        String API_KEY = "NCSQLXACP9UJZHR8";      // CoolSMS에서 발급받은 API Key
        String API_SECRET = "4K1UC5E25Q4YDU74KRUHBSAFZAB93RZY"; // CoolSMS에서 발급받은 API Secret
        String FROM_NUMBER = "01046624036"; // 등록된 발신번호

        // 2. 수신번호와 메시지
        String toNumber = "01046624036";   // 받을 사람 번호
        String messageText = "테스트 메시지입니다!";

        System.out.println("=== CoolSMS 테스트 시작 ===");
        System.out.println("발신번호: " + FROM_NUMBER);
        System.out.println("수신번호: " + toNumber);
        System.out.println("메시지: " + messageText);
        System.out.println("========================\n");

        try {
            // 3. CoolSMS 서비스 초기화 (한 줄!)
            DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(
                    API_KEY, API_SECRET, "https://api.coolsms.co.kr"
            );

            // 4. 메시지 객체 생성
            Message message = new Message();
            message.setFrom(FROM_NUMBER);
            message.setTo(toNumber);
            message.setText(messageText);

            // 5. 메시지 발송 (한 줄!)
            messageService.send(message);

            System.out.println("✅ 문자 발송 성공!");
            System.out.println("문자 메시지가 전송되었습니다.");

        } catch (NurigoMessageNotReceivedException e) {
            System.out.println("❌ 일부 수신자에게 발송 실패");
            System.out.println("실패 메시지: " + e.getFailedMessageList());
            System.out.println("성공 메시지: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("❌ 문자 발송 실패!");
            System.out.println("에러 메시지: " + e.getMessage());
            e.printStackTrace();
        }
    }
}