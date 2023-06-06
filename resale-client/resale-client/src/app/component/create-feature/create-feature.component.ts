import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-feature',
  templateUrl: './create-feature.component.html',
  styleUrls: ['./create-feature.component.css']
})
export class CreateFeatureComponent implements OnInit {
  baseUrl = environment.baseUrl;

  feature = new FormGroup({
    name: new FormControl('',
      [Validators.maxLength(50),
      Validators.minLength(2),
      Validators.required]),
    description: new FormControl('',
      [Validators.maxLength(1000)])
  });

  constructor(private http: HttpClient, private router:Router) { }

  ngOnInit(): void {
  }

  createFeature() {
    this.http.post(this.baseUrl + 'feature', {
      name: this.feature.value.name,
      description: this.feature.value.description
    }).subscribe((res:any)=>{
      this.router.navigate(['/administrate/feature']);
    })
  }
}
