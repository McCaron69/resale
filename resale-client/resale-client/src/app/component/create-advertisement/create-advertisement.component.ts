import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {Advertisement} from "../../domain/advertisement";
import {AuthorizationService} from "../../service/authorization.service";
import {Category} from "../../domain/category";
import {faSearch} from "@fortawesome/free-solid-svg-icons/faSearch";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {faTimes} from "@fortawesome/free-solid-svg-icons/faTimes";
import {faPlus} from "@fortawesome/free-solid-svg-icons/faPlus";
import {Feature} from "../../domain/feature";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";
import {Address} from "../../domain/address";
import {AddressService} from "../../service/address.service";

@Component({
  selector: 'app-create-advertisement',
  templateUrl: './create-advertisement.component.html',
  styleUrls: ['./create-advertisement.component.css']
})
export class CreateAdvertisementComponent implements OnInit {
  private baseUrl = environment.baseUrl;
  advertisement = this.fb.group({
    name: new FormControl('',
      [Validators.required,
      Validators.minLength(2),
      Validators.maxLength(50)]),
    description: new FormControl('',
      [Validators.maxLength(1000)]),
    price: new FormControl('',
      [Validators.min(0),
      Validators.max(50000)]),
  })

  faCross = faTimes
  images:any[]= [];
  words = new FormControl('');
  categories: Category[];
  addresses: Address[];
  currentCategory: Category | null;
  currentAddress: Address | null;
  features: Feature[] = [];
  totalCategoryPage: number;
  faSearch = faSearch;

  closeResult = '';
  categoryPage = 0;
  faPlus = faPlus;
  showFeatures = false;
  addressPage = 0;
  totalAddressPage: number;

  constructor(private http: HttpClient,
              private auth: AuthorizationService,
              private modalService: NgbModal,
              private fb: FormBuilder,
              private router:Router,
              public addressService:AddressService) {
  }

  ngOnInit(): void {
  }


  createAdvertisement() {
    const adv = {
      name: this.advertisement.value.name,
      description: this.advertisement.value.description,
      price: this.advertisement.value.price,
      user: this.auth.getUser(),
      category: this.currentCategory,
      features: this.features,
      address: this.currentAddress,
      images: []
    }
    console.log(adv);
    this.http.post<Advertisement>(this.baseUrl+"advertisement", adv).subscribe(res => {
      console.log(res);
      this.images.forEach(img=>{
        const form = new FormData();
        form.append('file',img,img.name);
        form.append('advertisementId', res.id.toString())
        this.http.post(this.baseUrl+"images/add", form)
          .subscribe(resp => {
            console.log(resp);
          });
      })
      this.router.navigate(['/user/advertisement']);
    })
  }

  onFileSelect(event:any) {
    this.images.push(event.target.files[0])
    console.log(this.images);
  }

  searchCategories(page:number) {
    this.categoryPage = page;
    this.http.get(this.baseUrl+"category/search", {
      params: new HttpParams()
        .append('words', this.words.value==null?'':this.words.value)
        .append('page', page)
    }).subscribe((res:any) => {
      this.categories = res.content;
      this.totalCategoryPage = res.totalPages;
    })
  }

  chooseCategory(category: Category, modal: any) {
    this.currentCategory = category;
    this.features = this.recursiveFeatures(category);
    this.categories = [];
    this.words.setValue('');
    modal.close('close');
  }

  recursiveFeatures(category:Category):Feature[]{
    let features = new Array<Feature>();
    category.features.forEach(f=>{
      features.push(f);
    })
    if(category.parent != null){
      this.recursiveFeatures(category.parent).forEach(f=>{
        features.push(f);
      })
    }
    features = this.deleteDuplicateFeatures(features);
    return features;
  }

  deleteCategory() {
    this.currentCategory = null;
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



  deleteImage(image: any) {
    const index = this.images.indexOf(image);
    if(index>-1)this.images.splice(index,1);
  }

  changeFeatureValue(event: any, feature: Feature) {
    feature.value = event.target.value;
  }

  openCategoryModal(content:any) {
    this.categories = [];
    this.open(content);
  }
  openAddressModal(content: any) {
    this.addresses = [];
    this.open(content);
  }
  open(content: any){
    this.words.setValue('');
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  deleteDuplicateFeatures(features : Array<Feature>) : Array<Feature> {
    const checkedFeatureIds = new Set();
    let uniqueFeatures = new Array<Feature>();

    for (const feature of features) {
      const currentFeatureId = feature.featureId;
      if(!checkedFeatureIds.has(currentFeatureId)) {
        uniqueFeatures.push(feature);
        checkedFeatureIds.add(currentFeatureId);
      }
    }

    return uniqueFeatures;
  }

  chooseAddress(address: Address, modal: any) {
    this.currentAddress = address;
    this.addresses = [];
    this.words.setValue('');
    modal.close('close');
  }

  searchAddresses(page: number) {
    this.addressPage = page;
    this.http.get(this.baseUrl+"address/search", {
      params: new HttpParams()
        .append('words', this.words.value==null?'':this.words.value)
        .append('page', page)
    }).subscribe((res:any) => {
      this.addresses = res.content;
      this.totalAddressPage = res.totalPages;
    })
  }
}
