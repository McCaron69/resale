package ee.tptlive.arturivushkin.resale.dao;

import ee.tptlive.arturivushkin.resale.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Page<User> findAllByOrderByLoginAsc(Pageable pageable);

    User findByLogin(String login);
}