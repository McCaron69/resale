package ee.tptlive.arturivushkin.resale.service;

import ee.tptlive.arturivushkin.resale.dao.AddressRepository;
import ee.tptlive.arturivushkin.resale.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AddressService {

  private AddressRepository addressRepository;


  public AddressService(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }


  public Address createAddress(Address address){
    return addressRepository.save(address);
  }


  public Optional<Address> findById(Long id){
    return addressRepository.findById(id);
  }

  public Page<Address> getPage(Integer page){
    return addressRepository.findAllByOrderByCountryAsc(PageRequest.of(page,10));
  }

  public Address updateAddress(Address address){
    return addressRepository.save(address);
  }


  public void deleteAddress(Long id){
    addressRepository.deleteById(id);
  }

  public Page<Address> search(Integer page, String words){
    return addressRepository.findByFields(words,PageRequest.of(page,10));
  }

}


