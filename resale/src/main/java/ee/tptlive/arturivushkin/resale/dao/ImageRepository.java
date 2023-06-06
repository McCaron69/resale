package ee.tptlive.arturivushkin.resale.dao;

import ee.tptlive.arturivushkin.resale.entity.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image,Long> {
}
