package kr.kro.moonlightmoist.shopapi.user.dto;

import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSearchCondition {
    private String searchType; //검색 타입(이름, 핸드폰 번호 등)
    private String searchKeyword;; //검색어
    private LocalDate startDate; //가입일 시작
    private LocalDate endDate; //가입일 종료
    private List<UserGrade> userGrade; //유저 등급들
    private Boolean smsAgreement; //SMS 수신 동의 (null:전체, true:수신, false:거부)
    private Boolean emailAgreement; //이메일 수신 동의 (null:전체, true:수신, false:거부)
    private List<Boolean> userStatuses; //회원 상태(deleted 필드 기준: false-정상, true-탈퇴)
}
