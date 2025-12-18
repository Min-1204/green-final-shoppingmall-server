package kr.kro.moonlightmoist.shopapi.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.kro.moonlightmoist.shopapi.user.domain.QUser;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.dto.UserSearchCondition;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class UserCustomRepositoryImpl implements UserCustomRepository{

    private final JPAQueryFactory queryFactory;

    public UserCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<User> search(UserSearchCondition condition) {
        QUser user = QUser.user;

        List<User> userList = queryFactory
                .selectFrom(user)
                .where(
                        searchFilter(condition.getSearchType(), condition.getSearchKeyword()),
                        dateFilter(
                                condition.getStartDate() != null ? condition.getStartDate().atStartOfDay() : null,
                                condition.getEndDate() != null ? condition.getEndDate().atTime(LocalTime.MAX) : null
                        ),
                        gradeFilter(condition.getUserGrade()),
                        smsAgreementFilter(condition.getSmsAgreement()),
                        emailAgreementFilter(condition.getEmailAgreement()),
                        deletedFilter(condition.getUserStatuses())
                )
                .fetch();

        return userList;
    }

    //검색어 필터 (이름, 핸드폰 번호 등)
    private BooleanExpression searchFilter(String searchType, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }

        QUser user = QUser.user;

        //searchType이 null이면 "이름"을 기본값으로
        if (searchType == null || searchType.equals("이름")) {
            return user.name.contains(keyword);
        } else if (searchType.equals("아이디")) {
            return user.loginId.contains(keyword);
        } else if (searchType.equals("핸드폰(네자리)")) {
            return user.phoneNumber.contains(keyword);
        }

        return null;
    }

    //가입일 필터
    private BooleanExpression dateFilter(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime == null && endDateTime == null) {
            return null;
        }

        QUser user = QUser.user;

        if (startDateTime != null && endDateTime != null) {
            return user.createdAt.between(startDateTime, endDateTime);
        } else if (startDateTime != null) {
            return user.createdAt.goe(startDateTime);
        } else if (endDateTime != null){
            return user.createdAt.loe(endDateTime);
        }

        return null;
    }

    //등급 필터
    private BooleanExpression gradeFilter(List<UserGrade> userGrade) {
        if (userGrade == null || userGrade.isEmpty()) {
            return null;
        }

        QUser user = QUser.user;
        return user.userGrade.in(userGrade);
    }

    //SMS 수신 동의 필터
    private BooleanExpression smsAgreementFilter(Boolean smsAgreement) {
        if (smsAgreement == null) {
            return null;
        }

        QUser user = QUser.user;
        return user.smsAgreement.eq(smsAgreement);
    }

    //이메일 수신 동의 필터
    private BooleanExpression emailAgreementFilter(Boolean emailAgreement) {
        if (emailAgreement == null) {
            return null;
        }

        QUser user = QUser.user;
        return user.emailAgreement.eq(emailAgreement);
    }

    //회원 상태 필터 (정상/탈퇴)
    private BooleanExpression deletedFilter(List<Boolean> userStatuses) {
        if (userStatuses == null || userStatuses.isEmpty()) {
            return null;
        }

        QUser user = QUser.user;
        return user.deleted.in(userStatuses);
    }
}
