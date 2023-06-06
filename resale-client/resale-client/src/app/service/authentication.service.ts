import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  http: HttpClient;
  requestHeader = new HttpHeaders({
    "No-Auth":"True"
  })
  private baseUrl = environment.baseUrl;
  constructor(http: HttpClient) {
    this.http = http;
  }
  public generateToken(loginData:any) {
    return this.http.post(this.baseUrl+"auth",
      loginData, {headers:this.requestHeader});
  }
  public registrate(registrationData: any) {
    return this.http.post(this.baseUrl+"register",
      registrationData, {headers:this.requestHeader});
  }
}
