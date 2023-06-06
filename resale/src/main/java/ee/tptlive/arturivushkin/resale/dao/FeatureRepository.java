package ee.tptlive.arturivushkin.resale.dao;

import ee.tptlive.arturivushkin.resale.entity.Feature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface FeatureRepository extends CrudRepository<Feature, Long> {
  Page<Feature> findAllByOrderByNameAsc(Pageable page);

  Page<Feature> findByNameContainsIgnoreCaseOrderByNameAsc(String name, Pageable pageable);
}
