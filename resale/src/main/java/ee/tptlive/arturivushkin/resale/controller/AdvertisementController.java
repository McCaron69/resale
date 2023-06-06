package ee.tptlive.arturivushkin.resale.controller;

import ee.tptlive.arturivushkin.resale.dto.AdvertisementDto;
import ee.tptlive.arturivushkin.resale.entity.Advertisement;
import ee.tptlive.arturivushkin.resale.service.AdvertisementService;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/advertisement", produces = "application/json")
public class AdvertisementController {

  private final AdvertisementService advertisementService;

  public AdvertisementController(AdvertisementService advertisementService) {
    this.advertisementService = advertisementService;
  }

  @GetMapping
  public Iterable<AdvertisementDto> getAdvertisements(@RequestParam int page) {
    Page<Advertisement> result = advertisementService.getPage(page);
    return new PageImpl<>(result.stream()
        .map(AdvertisementDto::fromAdvertisement)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }

  @PostMapping
  public ResponseEntity<AdvertisementDto> createAdvertisement(@RequestBody Advertisement advertisement) {
    advertisement.setPlacedAt(LocalDateTime.now());
    advertisement.getFeatures().removeIf(featureValue -> featureValue.getValue() == null);
    return new ResponseEntity<>(
        AdvertisementDto.fromAdvertisement(advertisementService.save(advertisement)),
        HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AdvertisementDto> getAdvertisement(@PathVariable Long id) {
    return advertisementService.getAdvertisement(id)
        .map(AdvertisementDto::fromAdvertisement)
        .map(ResponseEntity::ok)
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PatchMapping
  public ResponseEntity<AdvertisementDto> updateAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
    return advertisementService.updateAdvertisement(advertisementDto.toAdvertisement())
        .map(AdvertisementDto::fromAdvertisement)
        .map(ResponseEntity::ok)
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<AdvertisementDto> deleteAdvertisement(@PathVariable Long id) {
    return advertisementService.changeStatus(id, 3)
        .map(AdvertisementDto::fromAdvertisement)
        .map(ResponseEntity::ok)
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/category")
  public Iterable<AdvertisementDto> getByCategory(@RequestParam Long categoryId, @RequestParam(required = false) Integer page){
    if(page == null) page = 0;
    Page<Advertisement> result = advertisementService.getByCategory(categoryId,page);
    return new PageImpl<>(result.stream()
        .map(AdvertisementDto::fromAdvertisement)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }

  @GetMapping("/user/{id}")
  public Iterable<AdvertisementDto> getByUser(@PathVariable Long id, @RequestParam(required = false) Integer page){
    if(page == null) page = 0;
    Page<Advertisement> result = advertisementService.findByUser(id,page);
    return new PageImpl<>(result.stream()
        .map(AdvertisementDto::fromAdvertisement)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }


}
