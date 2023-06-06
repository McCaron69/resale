import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {faTimes} from "@fortawesome/free-solid-svg-icons/faTimes";
import {Image} from "../../domain/image";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {environment} from "../../../environments/environment";
import {Advertisement} from "../../domain/advertisement";
import {AuthorizationService} from "../../service/authorization.service";

@Component({
  selector: 'app-advertisement-edit',
  templateUrl: './advertisement-edit.component.html',
  styleUrls: ['./advertisement-edit.component.css']
})
export class AdvertisementEditComponent implements OnInit {
  baseUrl = environment.baseUrl;
  faTimes = faTimes

  adv: FormGroup;

  advertisement: Advertisement = JSON.parse(localStorage.getItem('adv')!);

  constructor(private http:HttpClient, public auth: AuthorizationService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.createForm();
    console.log(this.advertisement);
    localStorage.removeItem('adv');
  }

  deleteImage(image: Image) {
    this.http.delete(this.baseUrl+"images/" + image.id).subscribe(res=>{
      console.log(res);
      let index = this.advertisement.images.indexOf(image);
      if(index>-1)this.advertisement.images.splice(index,1);
    })
  }

  addImage(event: any) {
    let image = event.target.files[0];
    const form = new FormData();
    form.append('file',image,image.name);
    form.append('advertisementId', this.advertisement.id.toString())
    this.http.post(this.baseUrl+"images/add", form)
      .subscribe((resp: any) => {
        console.log(resp);
        this.advertisement.images.push(resp);
      });
  }

  saveAdvertisement() {

    this.http.patch(this.baseUrl+"advertisement", {
      id: this.advertisement.id,
      placedAt: this.advertisement.placedAt,
      images:this.advertisement.images,
      user: this.advertisement.user,
      name: this.adv.value.name,
      description: this.adv.value.description,
      price: this.adv.value.price,
      status: this.adv.value.status,
      features: this.advertisement.features,
      category: this.advertisement.category,
      address: this.advertisement.address
    }).subscribe((res:any)=>{
      console.log(res);
    })
  }

  updateFeature(i: number, event:any) {
    this.advertisement.features[i].value = event.target.value;
    console.log(this.advertisement);
  }

  private createForm() {

    this.adv = this.fb.group({
      name: new FormControl(this.advertisement?.name,
        [Validators.required,
        Validators.maxLength(50),
        Validators.minLength(2)]),
      description: new FormControl(this.advertisement?.description,
        [Validators.maxLength(1000)]),
      status: new FormControl(this.advertisement?.status),
      price: new FormControl(this.advertisement?.price,
        [Validators.min(0),
        Validators.max(50000)])
    })

  }
}
