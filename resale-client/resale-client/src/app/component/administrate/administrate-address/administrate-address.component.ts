import {Component, OnInit, TemplateRef} from '@angular/core';
import {Address} from "../../../domain/address";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-administrate-address',
  templateUrl: './administrate-address.component.html',
  styleUrls: ['./administrate-address.component.css']
})
export class AdministrateAddressComponent implements OnInit {
  private baseUrl = environment.baseUrl;

  addresses: Address[] = [];
  totalPages: number;
  page = 0;
  current: FormGroup;
  closeResult = '';
  constructor(private http: HttpClient, private modalService:NgbModal, private fb:FormBuilder) { }
  ngOnInit(): void {
    this.getAddresses(this.page);
  }

  getAddresses(page: number){
    this.page = page;
    return this.http.get(this.baseUrl+"address", {
      params: new HttpParams().append('page', page)
    }).subscribe((res:any) => {
      this.addresses = res.content;
      this.totalPages = res.totalPages;
    });
  }

  deleteAddress(address: Address) {
    const index = this.addresses.indexOf(address);
    if(index>-1){
      this.addresses.splice(index,1);
    }
    this.http.delete(this.baseUrl+"address/" + address.addressId).subscribe()
  }

  open(content:any, address:Address) {
    this.current = this.fb.group({
      addressId: new FormControl(address.addressId),
      country: new FormControl(address.country,
        [Validators.pattern('^[A-Za-zА-Яа-я -]*'),
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(50)]),
      region: new FormControl(address.region,
        [Validators.pattern('^[A-Za-zА-Яа-я -]*'),
          Validators.minLength(2),
          Validators.maxLength(50)]),
      city: new FormControl(address.city,
        [Validators.pattern('^[A-Za-zА-Яа-я -]*'),
          Validators.minLength(2),
          Validators.maxLength(50)]),
      part: new FormControl(address.part,
        [Validators.pattern('^[A-Za-zА-Яа-я -]*'),
          Validators.minLength(2),
          Validators.maxLength(50)])
    });
    this.modalService.open(content, { size: 'sm', windowClass: "modal-sm"}).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  updateAddress(modal: any) {
    if(this.current.valid) {
      this.http.patch(this.baseUrl + "address", this.current.value)
        .subscribe(res => {
            modal.close('update address' + res)
          }
        );
    }
  }
}
