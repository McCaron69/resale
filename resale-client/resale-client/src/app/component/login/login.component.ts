import {Component} from '@angular/core';
import {AuthenticationService} from "../../service/authentication.service";
import {AuthorizationService} from "../../service/authorization.service";
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ConfirmedValidator} from "../../service/confirm.password";
import {DateValidator} from "../../service/date.validator";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  registrationForm: FormGroup;
  loginForm = this.fb.group({
    login: new FormControl(''),
    password: new FormControl('')
  });

  constructor(private authenticationService: AuthenticationService,
              private authorizationService: AuthorizationService,
              private router: Router,
              private fb: FormBuilder) {
    this.registrationForm = this.fb.group({
      login: new FormControl('',
        [Validators.required,
          Validators.minLength(4),
          Validators.maxLength(20),
          Validators.pattern('[A-Za-z]+[A-Za-z0-9_]*')]),
      password: new FormControl('',
        [Validators.required,
          Validators.minLength(8),
          Validators.maxLength(64)]),
      confPassword: new FormControl('',
        [Validators.required,
          Validators.minLength(8),
          Validators.maxLength(64)]),
      firstName: new FormControl('',
        [Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
          Validators.pattern('[A-Za-zА-Яа-я]*')]),
      lastName: new FormControl('',
        [Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
          Validators.pattern('[A-Za-zА-Яа-я]*')]),
      age: new FormControl('',
        [Validators.required,
        DateValidator()]),
      phone: new FormControl('',[
        Validators.pattern('^[\+]?[0-9]{3,15}')
      ]),
      email: new FormControl('',
        [Validators.email])
    }, {validator: ConfirmedValidator('password', 'confPassword')});
  }


  doLogin() {
    this.authenticationService.generateToken(this.loginForm.value).subscribe(
      (response: any) => {
        this.loginForm.controls.login.setErrors(null)
        console.log(response);
        this.authorizationService.setToken(response.token);
        this.authorizationService.setRoles(response.user.roles);
        this.authorizationService.setUser(response.user);
        this.router.navigate(['/home']);
      }, (error) => {
        console.log(error);
        this.loginForm.controls.login.setErrors({'wrong': 'Неправильные имя пользователя или пароль'} )
      }
    );
  }

  registration() {
    if (this.registrationForm.valid) {
      this.authenticationService.registrate(this.registrationForm.value).subscribe((data: any) => {
        this.authorizationService.setToken(data.token);
        this.authorizationService.setRoles(data.user.roles);
        this.authorizationService.setUser(data.user);
        document.getElementById('closeModal')?.click();
        this.router.navigate(['/home']);
      }, (error) => {
        console.log(error);
        this.registrationForm.controls.login.setErrors({'exist':'Пользователь с такими именем уже существует'})
      });
    } else {

    }
  }

}
