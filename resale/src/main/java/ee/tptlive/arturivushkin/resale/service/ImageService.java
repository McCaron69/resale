package ee.tptlive.arturivushkin.resale.service;

import ee.tptlive.arturivushkin.resale.dao.ImageRepository;
import ee.tptlive.arturivushkin.resale.entity.Advertisement;
import ee.tptlive.arturivushkin.resale.entity.Image;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class ImageService {


  private ImageRepository imageRepository;

  @Value("${image.path}")
  private String path;

  private HttpServletRequest request;

  public ImageService(ImageRepository imageRepository, HttpServletRequest request) {
    this.imageRepository = imageRepository;
    this.request = request;
  }

  public Image addImage(MultipartFile file, Advertisement advertisement) throws IOException {
    File uploadDir = new File(Paths.get(path).toAbsolutePath().normalize().toUri());
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }
    String curDate = LocalDateTime.now().getNano() + "";
    String fileName =
        "image_" + curDate + "_" + file.getOriginalFilename().toLowerCase().replaceAll(" ", "-");
    file.transferTo(new File("%s\\%s".formatted(uploadDir, fileName)));

    String extension = file.getOriginalFilename();
    extension = extension.substring(extension.lastIndexOf("."));
    Image image = new Image();
    image.setFilename(fileName);
    image.setAdvertisement(advertisement);
    image.setExtension(extension);
    imageRepository.save(image);
    return image;
  }


  public Optional<Image> findImageById(Long id){
    return imageRepository.findById(id);
  }

  public Resource loadFileAsResource(String fileName)
      throws MalformedURLException {
    Path fileStorageLocation =
        Paths.get(path).toAbsolutePath().normalize();
    Path filePath = fileStorageLocation.resolve(fileName).normalize();
    return new UrlResource(filePath.toUri());
  }

  public void deleteImage(Long id) {
    imageRepository.deleteById(id);
  }
}
