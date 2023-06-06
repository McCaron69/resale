import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-create-address',
  templateUrl: './create-address.component.html',
  styleUrls: ['./create-address.component.css']
})
export class CreateAddressComponent implements OnInit {

  baseUrl = environment.baseUrl;

  address = this.fb.group({
    country: new FormControl('',
      [Validators.pattern('^[A-Za-zА-Яа-я -]*'),
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(50)]),
    region: new FormControl('',
      [Validators.pattern('^[A-Za-zА-Яа-я -]*'),
        Validators.minLength(2),
        Validators.maxLength(50)]),
    city: new FormControl('',
      [Validators.pattern('^[A-Za-zА-Яа-я -]*'),
        Validators.minLength(2),
        Validators.maxLength(50)]),
    part: new FormControl('',
      [Validators.pattern('^[A-Za-zА-Яа-я -]*'),
        Validators.minLength(2),
        Validators.maxLength(50)])
  });

  constructor(private http:HttpClient,private router:Router, private fb:FormBuilder) { }

  ngOnInit(): void {
  }

  createAddress() {
    if(this.address.valid) {
      this.http.post(this.baseUrl + 'address', this.address.value).subscribe((res: any) => {
        this.router.navigate(['/administrate/address']);
      })
    }
  }
}
