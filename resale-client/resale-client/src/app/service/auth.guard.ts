import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthorizationService} from "./authorization.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authorization:AuthorizationService,
              private router:Router) {
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.authorization.getToken()!==null) {
      const roles = route.data["roles"] as Array<string>;
      const match = this.authorization.roleMatch(roles);
      if(match){
        return true;
      } else {
        this.router.navigate(['/home']);
        return false;
      }
    }
    this.router.navigate(['/login']);
    return false;
  }

}
