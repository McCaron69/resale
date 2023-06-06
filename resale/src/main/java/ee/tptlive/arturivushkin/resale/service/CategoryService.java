package ee.tptlive.arturivushkin.resale.service;

import ee.tptlive.arturivushkin.resale.dao.CategoryRepository;
import ee.tptlive.arturivushkin.resale.dao.FeatureRepository;
import ee.tptlive.arturivushkin.resale.entity.Category;
import ee.tptlive.arturivushkin.resale.entity.Feature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  private final FeatureRepository featureRepository;

  public CategoryService(CategoryRepository categoryRepository, FeatureRepository featureRepository) {
    this.categoryRepository = categoryRepository;
    this.featureRepository = featureRepository;
  }

  public Category addFeatureToCategory(Long featureId, long categoryId){
    Feature feature = featureRepository.findById(featureId)
        .orElseThrow(()-> new IllegalArgumentException("not found feature with id: " + featureId));
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(()-> new IllegalArgumentException("not found category with id: " + categoryId));
    category.getFeatures().add(feature);
    return categoryRepository.save(category);
  }

  public Category findCategoryByName(String name) {
    return categoryRepository.findByName(name);
  }

  public Page<Category> searchByName(String words, Integer page){
    return categoryRepository.findByNameContainsOrderByNameAsc(words, PageRequest.of(page, 10));
  }

  public Category createCategory(Category category) {
    return categoryRepository.save(category);
  }

  public Page<Category> getPage(Integer page) {
    return categoryRepository.findAllByOrderByNameAsc(PageRequest.of(page, 10));
  }

  public Optional<Category> findById(Long id) {
    return categoryRepository.findById(id);
  }

  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }

  public Category deleteFeature(Long featureId, Long categoryId) {
    Category category = categoryRepository.findById(categoryId).get();
    category.getFeatures().removeIf(feature -> feature.getFeatureId().equals(featureId));
    categoryRepository.save(category);
    return category;
  }

  public Set<Category> findChildren(Long parentId) {
    Set<Category> children = new HashSet<>();
    if(parentId != null) {
      categoryRepository.findById(parentId).ifPresent(
          category -> children.addAll(categoryRepository.findByParentOrderByNameAsc(category)));
    } else children.addAll(categoryRepository.findByParentOrderByNameAsc(null));
    return children;
  }

  public Category updateCategory(Category category) {
    if(category.getCategoryId()==null) throw new RuntimeException("categoryId could not be null");
    return categoryRepository.save(category);
  }
}
