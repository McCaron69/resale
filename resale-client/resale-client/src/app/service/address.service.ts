import {Address} from "../domain/address";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AddressService {
  getFormattedAddress(address : Address) : string {

    let formattedString = "";

    if (address == null) return formattedString;

    if (address.country != null) {
      formattedString += address.country;
    }
    if (address.region != null) {
      formattedString += ", ";
      formattedString += address.region;
    }
    if (address.city != null) {
      formattedString += ", ";
      formattedString += address.city;
    }
    if (address.part != null) {
      formattedString += ", ";
      formattedString += address.part;
    }

    return formattedString;
  }
}
