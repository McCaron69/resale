import { Injectable } from '@angular/core';
import {User} from "../domain/user";

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  constructor() { }


  public setUser(user:User){
    localStorage.setItem('user',JSON.stringify(user));
  }

  public getUser(){
    let user = localStorage.getItem('user');
    if(user != null){
      return JSON.parse(user);
    }
    return null;
  }

  public setRoles(roles:[]){
    localStorage.setItem('roles',JSON.stringify(roles));
  }

  public getRoles():[]{
    let roles = localStorage.getItem('roles');
    if(roles != null){
      return JSON.parse(roles);
    }
    return [];
  }

  public setToken(jwtToken:string){
    localStorage.setItem('jwtToken', jwtToken);
  }

  public getToken():string{
    return <string>localStorage.getItem('jwtToken');
  }

  public clear(){
    localStorage.clear();
  }

  public isSignIn(){
    return this.getRoles()!=null && this.getToken()!=null;
  }
  public roleMatch(allowedRoles:String[]):boolean{
    const userRoles:any = this.getRoles();
    for (let i = 0; i < userRoles.length; i++) {
      for (let j = 0; j < allowedRoles.length; j++) {
        if(allowedRoles[j] === userRoles[i].name){
          return true;
        }
      }
    }
    return false;
  }
}
