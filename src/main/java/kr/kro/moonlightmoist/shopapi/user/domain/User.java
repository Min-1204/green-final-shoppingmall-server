package kr.kro.moonlightmoist.shopapi.user.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.user.dto.UserModifyRequest;
import lombok.*;
import org.hibernate.type.EntityType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // DB와 1:1 매핑되는 클래스  JPA가 관리한다.
@Table(name = "users") // 테이블 어노테이션
@Getter
@Setter
@Builder
@AllArgsConstructor // 빌더를 쓰려면 올아그스먼트컨트럭쳐 어노테이션이 필요하다.
@NoArgsConstructor // JPA Entity는 기본생성자가 필수
@ToString
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // not null
    private String loginId;

    @Column(name = "password_Hash", nullable = false) // not null
    private String password;

    @Column(nullable = false) // not null
    private String name;

    @Column(nullable = false, unique = true, length = 11) // not null
    private String phoneNumber;

    @Column(nullable = false, unique = true) // not null
    private String email;

    @Column(nullable = false) // not null
    private LocalDate birthDate;

    @Column(nullable = false) // not null
    private String postalCode; // 우편번호

    @Column(nullable = false) // not null
    private String address; // 기본주소

    @Column(nullable = false) // not null
    private String addressDetail; // 상세주소

    @Column(nullable = false)
    private boolean emailAgreement; // 이메일 마케팅 동의 알림


    @Column(nullable = false)
    private boolean smsAgreement; // SMS 마케팅 동의 알림

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted; // 회원탈퇴 여부 컬럼

    private LocalDateTime deletedAt; // 회원탈퇴 일자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default // 기본값 적용
    private UserRole userRole = UserRole.USER;

    // 여러명의 유저는 회원등급을 가진다.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default // 기본값 적용
    private UserGrade userGrade = UserGrade.BRONZE;   // 회원 등급

    



    
    // 메서드
    public void updateAddressAndAgreement (String address, String addressDetail, String postalCode, boolean emailAgreement, boolean smsAgreement) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.postalCode = postalCode;
        this.emailAgreement = emailAgreement;
        this.smsAgreement = smsAgreement;
    }

    public void updateUserGrade (UserGrade userGrade) {
        this.userGrade = userGrade;
    }

    public void withdrawal() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void RollBackWithdrawal() {
        this.deleted = false;
        this.deletedAt = null;
    }

    public void updateProfile(UserModifyRequest request) {
        this.name = request.getName();
        this.phoneNumber = request.getPhoneNumber();
        this.email = request.getEmail();
        this.birthDate = request.getBirthDate();
        this.postalCode = request.getPostalCode();
        this.address = request.getAddress();
        this.addressDetail = request.getAddressDetail();
        this.emailAgreement = request.isEmailAgreement();
        this.smsAgreement = request.isSmsAgreement();
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // 12-4 추가
    public void withdraw() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    // 12-10 추가 PasswordEncoder 사용
    public void encodePassword (PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        // 암호화 필드저장
    }

    // 회원 권한 변경
    public void changeRole(UserRole newRole) {
        if (newRole == null) {
            throw new IllegalArgumentException("role must not be null");
        }
        this.userRole = newRole;
    }



}
