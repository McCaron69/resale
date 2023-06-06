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
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long addressId;

  private String country;
  private String region;

  private String city;

  private String part;

}
