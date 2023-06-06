package ee.tptlive.arturivushkin.resale.controller;

import ee.tptlive.arturivushkin.resale.dto.CategoryDto;
import ee.tptlive.arturivushkin.resale.entity.Category;
import ee.tptlive.arturivushkin.resale.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

  private final CategoryService service;

  public CategoryController(CategoryService service) {
    this.service = service;
  }


  @GetMapping("/children")
  public Iterable<CategoryDto> getChildren(@RequestParam(required = false) Long parentId){
    return service.findChildren(parentId).stream()
        .map(CategoryDto::fromCategory)
        .collect(Collectors.toSet());
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto category) {
    return new ResponseEntity<>(CategoryDto.fromCategory(
        service.createCategory(category.toCategory())),
        HttpStatus.CREATED);
  }
  record CategoryFeature(Long categoryId, Long featureId){}
  @PatchMapping("/feature/add")
  public ResponseEntity<CategoryDto> addFeature(@RequestBody CategoryFeature temp) {
    return new ResponseEntity<>(CategoryDto.fromCategory(
        service.addFeatureToCategory(temp.featureId, temp.categoryId)),
        HttpStatus.OK);
  }


  @PatchMapping
  public ResponseEntity<CategoryDto> updateCategory(@RequestBody Category category){
    return ResponseEntity
        .ok(CategoryDto.fromCategory(service.updateCategory(category)));
  }


  @GetMapping("/page")
  public Iterable<CategoryDto> getPage(@RequestParam Integer page){
    Page<Category> result = service.getPage(page);
    return new PageImpl<>(result
        .map(CategoryDto::fromCategory)
        .toList(),PageRequest.of(page,10),result.getTotalElements());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> findCategory(@PathVariable Long id){
    return service.findById(id)
        .map(CategoryDto::fromCategory)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long id){
    service.deleteCategory(id);
    return ResponseEntity.ok("delete category with id: " + id);
  }

  @PatchMapping("/feature/delete")
  public ResponseEntity<CategoryDto> deleteFeature(@RequestBody CategoryFeature temp) {
    return new ResponseEntity<>(CategoryDto.fromCategory(
        service.deleteFeature(temp.featureId, temp.categoryId)),
        HttpStatus.OK);
  }

  @GetMapping("/search")
  public Iterable<CategoryDto> findByName(@RequestParam String words, @RequestParam(required = false) Integer page){
    page = page==null?0:page;
    Page<Category> result = service.searchByName(words,page);
    return new PageImpl<>(result
        .map(CategoryDto::fromCategory)
        .toList(),PageRequest.of(page,10),result.getTotalElements());

  }
}
