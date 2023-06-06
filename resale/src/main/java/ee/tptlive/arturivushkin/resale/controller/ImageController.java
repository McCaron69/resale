package ee.tptlive.arturivushkin.resale.controller;

import ee.tptlive.arturivushkin.resale.entity.Advertisement;
import ee.tptlive.arturivushkin.resale.entity.Image;
import ee.tptlive.arturivushkin.resale.service.AdvertisementService;
import ee.tptlive.arturivushkin.resale.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/images")
public class ImageController {


  private final ImageService imageService;
  private final AdvertisementService advertisementService;

  public ImageController(ImageService imageService, AdvertisementService advertisementService) {
    this.imageService = imageService;
    this.advertisementService = advertisementService;
  }


  @PostMapping(value = "/add", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Image> uploadAttachment(
      @RequestPart(value = "file") MultipartFile file, @RequestParam Long advertisementId) {
    Advertisement advertisement = advertisementService.getAdvertisement(advertisementId).get();
    Image image = null;
    try {
      image = imageService.addImage(file,advertisement);
    } catch (IOException e) {
    }
    return ResponseEntity.ok(image);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<Resource> getImage(@PathVariable Long id, HttpServletRequest request) throws IOException {
    Image image = imageService.findImageById(id).get();
    Resource resource = imageService.loadFileAsResource(image.getFilename());
    String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    if (contentType == null) {
      contentType = "application/octet-stream";
    }
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "image; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteImage(@PathVariable Long id){
    Optional<Image> image = imageService.findImageById(id);
    image.ifPresent(img->{
      try {
        imageService.loadFileAsResource(img.getFilename()).getFile().delete();
        imageService.deleteImage(id);
      } catch (MalformedURLException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    return ResponseEntity.ok("delete image with id: " + id);
  }
}
