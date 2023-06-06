import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {AuthorizationService} from "./authorization.service";
import {Router} from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authorization:AuthorizationService,
              private router:Router){}
  intercept(req: HttpRequest<any>,
            next: HttpHandler):
    Observable<HttpEvent<any>> {
    console.log(req);
    if(req.headers.get("No-Auth")==='True'){
      return next.handle(req.clone());
    }
    const token = this.authorization.getToken();
    if(token != null) {
      req = this.addToken(req, token);
    }else req = this.cross(req);
    return next.handle(req).pipe(
      catchError((err:HttpErrorResponse)=>{
          console.log(err.status);
          if(err.status===401){
            this.router.navigate(['/login']);
          }else if(err.status===403){
            this.router.navigate(['/home']);
          }
          return throwError(()=>err);
        }
      ))
  }
  private addToken(request:HttpRequest<any>,token:string){
    return request.clone(
      {
        setHeaders:{
          Authorization:`Bearer ${token}`
        }
      }
    )
  }

  private cross(req: HttpRequest<any>) {
    return req.clone({
      setHeaders:{
        'Access-Control-Allow-Origin':'true'
      }
    });
  }
}
