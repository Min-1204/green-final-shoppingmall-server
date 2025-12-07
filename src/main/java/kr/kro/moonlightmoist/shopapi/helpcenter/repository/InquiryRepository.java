package kr.kro.moonlightmoist.shopapi.helpcenter.repository;

import kr.kro.moonlightmoist.shopapi.helpcenter.domain.Inquiry;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByUser(User user);
}
