package ee.tptlive.arturivushkin.resale.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import ee.tptlive.arturivushkin.resale.dao.AdvertisementRepository;
import ee.tptlive.arturivushkin.resale.dao.CategoryRepository;
import ee.tptlive.arturivushkin.resale.entity.Advertisement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceImplTest {

  @InjectMocks
  private AdvertisementServiceImpl advertisementService;

  @Mock
  private AdvertisementRepository advertisementRepository;

  @Mock
  private CategoryRepository categoryRepository;


  @Test
  void testGetPage() {
    // Arrange
    int page = 0;
    Page<Advertisement> expectedPage = mock(Page.class);
    when(advertisementRepository.findAllByOrderByNameAsc(PageRequest.of(page, 10))).thenReturn(expectedPage);

    // Act
    Page<Advertisement> result = advertisementService.getPage(page);

    // Assert
    assertEquals(expectedPage, result);
    verify(advertisementRepository).findAllByOrderByNameAsc(PageRequest.of(page, 10));
  }

  @Test
  void testSave() {
    // Arrange
    Advertisement advertisement = mock(Advertisement.class);
    Advertisement savedAdvertisement = mock(Advertisement.class);
    when(advertisementRepository.save(advertisement)).thenReturn(savedAdvertisement);

    // Act
    Advertisement result = advertisementService.save(advertisement);

    // Assert
    assertEquals(savedAdvertisement, result);
    verify(advertisementRepository).save(advertisement);
  }

  @Test
  void testGetAdvertisement() {
    // Arrange
    Long id = 1L;
    Advertisement expectedAdvertisement = mock(Advertisement.class);
    when(advertisementRepository.findById(id)).thenReturn(Optional.of(expectedAdvertisement));

    // Act
    Optional<Advertisement> result = advertisementService.getAdvertisement(id);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(expectedAdvertisement, result.get());
    verify(advertisementRepository).findById(id);
  }


}