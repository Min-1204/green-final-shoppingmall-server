package kr.kro.moonlightmoist.shopapi.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class UserGradeRepositoryUnitTest {

    @Autowired
    private UserGradeRepository userGradeRepository;

//    @Test
//    @DisplayName("유저등급 생성테스트")
//    public void createUserGrade() {
//
//    }


//    @Test
//    @DisplayName("유저등급 수정테스트")
//    public void updateUserGrade() {
//
//
//    }

}