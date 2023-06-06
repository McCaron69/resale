package ee.tptlive.arturivushkin.resale.entity;



import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Advertisement {

  @Id
  @Column(name = "ad_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long adId;
  private String name;
  private String description;
  private int status;
  private LocalDateTime placedAt;

  private Double price;

  @OneToMany(mappedBy = "advertisement", cascade = {CascadeType.PERSIST})
  private List<Image> images;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
  @JoinColumn(name = "address_id")
  private Address address;


  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @ElementCollection
  @CollectionTable(name = "advertisement_feature",
  joinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "ad_id"))
  private Set<FeatureValue> features;
}
