package ee.tptlive.arturivushkin.resale.dao;

import ee.tptlive.arturivushkin.resale.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
  Role findByName(String name);

  Page<Role> findAll(Pageable page);
}