import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {AuthorizationService} from "../../service/authorization.service";
import {environment} from "../../../environments/environment";
import {faCheck} from "@fortawesome/free-solid-svg-icons/faCheck";
import {User} from "../../domain/user";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  faCheck = faCheck;
  baseUrl = environment.baseUrl;
  form:FormGroup;

  isSave = false;

  constructor(private http:HttpClient,
              private auth:AuthorizationService,
              private fb:FormBuilder) { }

  ngOnInit(): void {
    let user = this.auth.getUser();
    this.form =  this.form = this.fb.group({
      userId: new FormControl(user.userId),
      login: new FormControl(user.login,
        [Validators.required,
          Validators.minLength(4),
          Validators.maxLength(20),
          Validators.pattern('[A-Za-z]+[A-Za-z0-9_]*')]),
      firstName: new FormControl(user.firstName,
        [Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
          Validators.pattern('[A-Za-zА-Яа-я]*')]),
      lastName: new FormControl(user.lastName,
        [Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
          Validators.pattern('[A-Za-zА-Яа-я]*')]),
      age: new FormControl(user.age,
        [Validators.required]),
      phone: new FormControl(user.phone,
        [Validators.pattern('^[\+]?[0-9]{3,15}')
        ]),
      email: new FormControl(user.email,
        [Validators.email])
    });
  }

  updateUser() {
    this.http.patch<User>(this.baseUrl + 'user', this.form.value)
      .subscribe(res=>{
        console.log(res);
        this.isSave = true;
        this.auth.setUser(res);
      })
  }
}
