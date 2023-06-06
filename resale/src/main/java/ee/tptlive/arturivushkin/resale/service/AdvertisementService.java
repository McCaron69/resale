package ee.tptlive.arturivushkin.resale.service;

import ee.tptlive.arturivushkin.resale.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AdvertisementService {
  Page<Advertisement> getPage(int page);

  Page<Advertisement> findByUser(Long userId, int page);

  Advertisement save(Advertisement advertisement);

  Optional<Advertisement> getAdvertisement(Long id);

  @Transactional
  Optional<Advertisement> updateAdvertisement(Advertisement advertisement);

  Optional<Advertisement> changeStatus(Long id, int status);

  default boolean validate(int status) {
    return status < 4 && status > 0;
  }

  Page<Advertisement> search(String words, Integer page);

  Page<Advertisement> getByCategory(Long categoryId, Integer page);
}
