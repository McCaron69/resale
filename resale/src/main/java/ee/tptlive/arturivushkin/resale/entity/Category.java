package ee.tptlive.arturivushkin.resale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;

  private String name;

  @ManyToMany()
  @JoinTable(name = "category_feature",
  joinColumns = @JoinColumn(name = "category_id"),
  inverseJoinColumns = @JoinColumn(name = "feature_id"))
  private Set<Feature> features;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id")
  private Category parent;
}
