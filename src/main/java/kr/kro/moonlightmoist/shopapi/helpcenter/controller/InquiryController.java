package kr.kro.moonlightmoist.shopapi.helpcenter.controller;


import kr.kro.moonlightmoist.shopapi.helpcenter.dto.*;
import kr.kro.moonlightmoist.shopapi.helpcenter.service.InquiryService;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inquiry")
@Slf4j
@CrossOrigin(origins = "*",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE})
public class InquiryController {

    private final InquiryService service;

    @PostMapping("/add")
    public ResponseEntity<InquiryCreateResponse> createInquiry (@RequestBody InquiryCreateRequest request) {
        InquiryCreateResponse response = service.registerInquiry(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getRead")
    public ResponseEntity<InquiryListDTO> readInquiry (@RequestParam String loginId) {
        InquiryListDTO inquiryList = service.getInquiryList(loginId);
        return ResponseEntity.ok(inquiryList);
    }

    @PutMapping("/inquiry-modify/{inquiryId}")
    public ResponseEntity<InquiryModifyResponse> modifyInquiry (@PathVariable Long inquiryId,
                                                                @RequestBody InquiryModifyRequest request,
                                                                @RequestParam String loginId) {
        InquiryModifyResponse response = service.modifyInquiry(inquiryId, request, loginId);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/inquiry-delete/{id}")
    public ResponseEntity<?> deleteInquiry(@PathVariable Long id,
                                           @RequestParam String loginId) {

        service.deleteInquiry(id, loginId);

        return ResponseEntity.ok().body("success");
    }

}


