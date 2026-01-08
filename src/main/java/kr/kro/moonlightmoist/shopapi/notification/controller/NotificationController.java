package kr.kro.moonlightmoist.shopapi.notification.controller;

import kr.kro.moonlightmoist.shopapi.notification.dto.SmsRequest;
import kr.kro.moonlightmoist.shopapi.notification.exception.SmsNotReceivedException;
import kr.kro.moonlightmoist.shopapi.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/sms")
    public ResponseEntity<String> sendSmsMessage(@RequestBody SmsRequest req) {
        notificationService.sendBatchSmsMessage(req.getUserIds(), req.getMessage());

//        throw new SmsNotReceivedException();

        return ResponseEntity.ok("ok");
    }
}
