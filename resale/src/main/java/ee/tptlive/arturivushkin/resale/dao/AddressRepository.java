package ee.tptlive.arturivushkin.resale.dao;

import ee.tptlive.arturivushkin.resale.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface AddressRepository extends CrudRepository<Address, Long> {

  Page<Address> findAllByOrderByCountryAsc(Pageable page);

  @Query("select a from Address a " +
      "where a.country like %:words% " +
      "or a.region like %:words% " +
      "or a.city like %:words% " +
      "or a.part like %:words% ")
  Page<Address> findByFields(String words, Pageable pageable);
}