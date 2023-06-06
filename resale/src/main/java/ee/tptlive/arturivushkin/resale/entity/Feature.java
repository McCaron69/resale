package ee.tptlive.arturivushkin.resale.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Setter
@Getter
@RequiredArgsConstructor
@ToString
public class Feature {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long featureId;

  private String name;

  private String description;

}
