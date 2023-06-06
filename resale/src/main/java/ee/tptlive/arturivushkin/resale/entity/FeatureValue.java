package ee.tptlive.arturivushkin.resale.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class FeatureValue {
  private Long featureId;

  private String value;
}
