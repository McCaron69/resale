package ee.tptlive.arturivushkin.resale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "image_id", nullable = false)
  private Long id;

  private String filename;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "advertisement_id")
  private Advertisement advertisement;

  private String extension;

}
