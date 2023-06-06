import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Advertisement} from "../../../domain/advertisement";
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-administrate-advertisement',
  templateUrl: './administrate-advertisement.component.html',
  styleUrls: ['./administrate-advertisement.component.css']
})
export class AdministrateAdvertisementComponent implements OnInit {

  private baseUrl = environment.baseUrl
  advertisements: Advertisement[];
  page:number = 0;
  totalPages: number;
  constructor(private http:HttpClient, private router:Router) { }
  ngOnInit(): void {
    this.loadAdvertisement(this.page);
  }
  loadAdvertisement(page:number){
    this.page = page;
    return this.http.get(this.baseUrl+"advertisement",{
      params: new HttpParams().append('page', page)
    }).subscribe((res:any)=>{
      this.advertisements = res.content;
      this.totalPages = res.totalPages;
    })
  }

  archiveAdvertisement(advertisement:Advertisement) {
    this.http.delete<Advertisement>(this.baseUrl+"advertisement/" + advertisement.id).subscribe(res=>{
      const index = this.advertisements.indexOf(advertisement);
      if(~index){
        this.advertisements[index] = res;
      }
    })
  }

  getAdvertisement(advertisement: Advertisement) {
    localStorage.setItem('adv', JSON.stringify(advertisement));
    this.router.navigate(['/advertisement/edit']);
  }
}
