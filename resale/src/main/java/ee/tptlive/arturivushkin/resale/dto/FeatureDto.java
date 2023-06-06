package ee.tptlive.arturivushkin.resale.dto;

import ee.tptlive.arturivushkin.resale.entity.Feature;
import ee.tptlive.arturivushkin.resale.entity.FeatureValue;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class FeatureDto {

  private Long featureId;
  private String name;
  private String description;
  private String value;


  public static FeatureDto fromFeature(Feature feature){
    return FeatureDto.builder()
        .featureId(feature.getFeatureId())
        .name(feature.getName())
        .description(feature.getDescription())
        .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FeatureDto that = (FeatureDto) o;
    return Objects.equals(featureId, that.featureId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(featureId);
  }

  public Feature toFeature() {
    Feature feature = new Feature();
    feature.setFeatureId(featureId);
    feature.setName(name);
    feature.setDescription(description);
    return feature;
  }

  public FeatureValue toFeatureValue(){
    FeatureValue featureValue = new FeatureValue();
    featureValue.setValue(value);
    featureValue.setFeatureId(featureId);
    return featureValue;
  }
}
