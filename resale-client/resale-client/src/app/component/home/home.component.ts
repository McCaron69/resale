import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Category} from "../../domain/category";
import {faAd} from "@fortawesome/free-solid-svg-icons/faAd";
import {Advertisement} from "../../domain/advertisement";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons/faArrowLeft";
import {environment} from "../../../environments/environment";
import {AddressService} from "../../service/address.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  baseUrl = environment.baseUrl;

  faBack = faArrowLeft;
  faAd = faAd;
  categories: Category[];

  parent: Category|null;

  advertisements: Advertisement[] = [];
  page = 0;
  totalPages: number;
  constructor(private http:HttpClient,
              public addressService:AddressService) { }

  ngOnInit(): void {
    this.getCategories(null);
  }

  up(parent:Category|null){
    this.parent = parent;
    this.getCategories(parent);
  }
  getCategories(parent:Category|null){
    if(parent != null){
    this.http.get<Category[]>(this.baseUrl+"category/children", {
      params: new HttpParams().append('parentId', parent.categoryId.toString())
    }).subscribe(res => {
      this.categories = res.sort(this.sortByName);
    });
    this.getAdv(0,parent);
    }else {
      this.http.get<Category[]>(this.baseUrl+"category/children").subscribe(res => {
        this.categories = res.sort(this.sortByName);
      })
      this.advertisements = [];
    }
  }

  getAdv(page:number, category:Category){
    this.page =page;
    this.http.get(this.baseUrl+"advertisement/category", {
      params: new HttpParams()
        .append('categoryId', category.categoryId.toString())
        .append('page', page)
    }).subscribe((res:any)=>{
      this.advertisements = res.content;
      this.totalPages = res.totalPages;
    })
  }

  back() {
    this.parent = this.parent!.parent;
    this.getCategories(this.parent);
  }
  sortByName(a: Category, b: Category): number {
    return a.name<b.name?-1:1
  }
}
