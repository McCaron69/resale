package ee.tptlive.arturivushkin.resale.dto;

import ee.tptlive.arturivushkin.resale.entity.*;
import ee.tptlive.arturivushkin.resale.entity.Address;
import ee.tptlive.arturivushkin.resale.entity.Advertisement;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AdvertisementDto {
  private long id;
  private String name;
  private String description;


  private Address address;

  private UserDto user;
  private LocalDateTime placedAt;
  private int status;
  private CategoryDto category;

  private List<ImageDto> images;

  private Double price;

  private List<FeatureDto> features;


  public Advertisement toAdvertisement() {
    Advertisement advertisement = new Advertisement();
    advertisement.setAdId(this.id);
    advertisement.setPlacedAt(this.placedAt);
    advertisement.setUser(this.user.toUser());
    advertisement.setStatus(this.status);
    advertisement.setAddress(this.address);
    advertisement.setCategory(category!=null?this.category.toCategory():null);
    advertisement.setName(this.name);
    advertisement.setDescription(this.description);
    advertisement.setPrice(this.price);
    advertisement.setImages(images.stream().map(ImageDto::toImage).toList());
    advertisement.setFeatures(features.stream()
        .map(FeatureDto::toFeatureValue)
        .collect(Collectors.toSet()));
    return advertisement;
  }

  public static AdvertisementDto fromAdvertisement(Advertisement advertisement) {
    List<FeatureDto> features = new ArrayList<>();
    if (advertisement.getCategory() != null && advertisement.getCategory().getFeatures() != null) {
      advertisement.getFeatures().forEach(featureValue -> CategoryDto
          .fromCategory(advertisement.getCategory()).getFeatures().stream()
          .filter(feature -> feature.getFeatureId().equals(featureValue.getFeatureId()))
          .findFirst()
          .ifPresent(feature -> {
            feature.setValue(featureValue.getValue());
            features.add(feature);
          }));
    }
    return AdvertisementDto.builder()
        .user(UserDto.fromUser(advertisement.getUser()))
        .id(advertisement.getAdId())
        .address(advertisement.getAddress())
        .category(CategoryDto.fromCategory(advertisement.getCategory()))
        .name(advertisement.getName())
        .description(advertisement.getDescription())
        .placedAt(advertisement.getPlacedAt())
        .status(advertisement.getStatus())
        .price(advertisement.getPrice())
        .images(advertisement.getImages()!=null?
            advertisement.getImages().stream().map(ImageDto::fromImage).toList(): Collections.emptyList())
        .features(features)
        .build();
  }
}