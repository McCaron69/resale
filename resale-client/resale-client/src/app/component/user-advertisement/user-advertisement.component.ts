import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {User} from "../../domain/user";
import {Advertisement} from "../../domain/advertisement";
import {AuthorizationService} from "../../service/authorization.service";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-advertisement',
  templateUrl: './user-advertisement.component.html',
  styleUrls: ['./user-advertisement.component.css']
})
export class UserAdvertisementComponent implements OnInit {
  advertisements: Advertisement[];
  totalPages = 0;
  page = 0;
  user: User;
  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient,
              private auth: AuthorizationService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.user = this.auth.getUser();
    this.getUserAdvertisement(this.page);
  }

  getUserAdvertisement(page: number) {
    this.http.get(this.baseUrl + "advertisement/user/" + this.user.userId,
      {params: new HttpParams().append('page', page)})
      .subscribe((res: any) => {
        this.advertisements = res.content;
        this.totalPages = res.totalPages;
        this.page = page;
      });
  }

  getAdvertisement(advertisement: Advertisement) {
    localStorage.setItem('adv', JSON.stringify(advertisement));
    this.router.navigate(['/advertisement/edit']);
  }

  archiveAdvertisement(advertisement:Advertisement) {
    this.http.delete<Advertisement>(this.baseUrl+"advertisement/" + advertisement.id).subscribe(res=>{
      const index = this.advertisements.indexOf(advertisement);
      if(~index){
        this.advertisements[index] = res;
      }
    })
  }
}
