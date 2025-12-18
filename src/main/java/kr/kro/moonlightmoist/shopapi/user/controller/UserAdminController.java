package kr.kro.moonlightmoist.shopapi.user.controller;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.UserSearchCondition;
import kr.kro.moonlightmoist.shopapi.user.service.UserAdminService;
import kr.kro.moonlightmoist.shopapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/user") //관리자 전용 경로
@CrossOrigin(origins = "http://localhost:5137")
public class UserAdminController {

    private final UserAdminService userAdminService;

    @PostMapping({"/search", "/search/"})
    public ResponseEntity<List<User>> searchUser(@RequestBody UserSearchCondition condition) {
        log.info("검색 조건: {}", condition);

        List<User> userList = userAdminService.searchUsers(condition);

        log.info("검색 결과: {}", userList.size());
        return ResponseEntity.ok(userList);
    }
}
