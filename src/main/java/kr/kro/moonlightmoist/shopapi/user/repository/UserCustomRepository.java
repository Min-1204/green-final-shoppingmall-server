package kr.kro.moonlightmoist.shopapi.user.repository;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.UserSearchCondition;

import java.util.List;

public interface UserCustomRepository {
    List<User> search(UserSearchCondition condition);
}
