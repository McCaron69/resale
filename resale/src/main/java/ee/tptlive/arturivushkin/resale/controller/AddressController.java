package ee.tptlive.arturivushkin.resale.controller;

import ee.tptlive.arturivushkin.resale.entity.Address;
import ee.tptlive.arturivushkin.resale.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/address", produces = "application/json")
public class AddressController {

  private final AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }


  @GetMapping
  public Iterable<Address> getPage(@RequestParam Integer page){
    return addressService.getPage(page);
  }


  @GetMapping("/{id}")
  public ResponseEntity<Address> getAddress(@PathVariable Long id){
    return addressService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }

  @PatchMapping
  public ResponseEntity<Address> updateAddress(@RequestBody Address address){
    return ResponseEntity.ok(addressService.updateAddress(address));
  }

  @DeleteMapping("/{id}")
  public Long deleteAddress(@PathVariable Long id){
    addressService.deleteAddress(id);
    return id;
  }
  
  @PostMapping
  public ResponseEntity<Address> createAddress(@RequestBody Address address){
    return ResponseEntity.ok(addressService.createAddress(address));
  }

  @GetMapping("/search")
  public Iterable<Address> search(@RequestParam Integer page, @RequestParam String words){
    return addressService.search(page,words);
  }

}
