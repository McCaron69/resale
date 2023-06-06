package ee.tptlive.arturivushkin.resale.dto;

import ee.tptlive.arturivushkin.resale.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class CategoryDto {
    private Long categoryId;
    private String name;
    private Set<FeatureDto> features;

    private Category parent;

    public static CategoryDto fromCategory(Category category){
      if(category == null) return null;
      return CategoryDto.builder()
          .categoryId(category.getCategoryId())
          .features(getFeatures(category))
          .name(category.getName())
          .parent(category.getParent())
          .build();
    }

    private static Set<FeatureDto> getFeatures(Category category){
      Set<FeatureDto> features = category.getFeatures().stream()
          .map(FeatureDto::fromFeature)
          .collect(Collectors.toSet());
      if(category.getParent()!=null)
          features.addAll(getFeatures(category.getParent()));
      return features;
    }

  public Category toCategory() {
    Category category = new Category();
    category.setCategoryId(categoryId);
    category.setName(name);
    if(features != null) {
      category.setFeatures(features.stream()
          .map(FeatureDto::toFeature)
          .collect(Collectors.toSet()));
    }else category.setFeatures(Collections.emptySet());
    category.setParent(parent);
    return category;
  }
}
