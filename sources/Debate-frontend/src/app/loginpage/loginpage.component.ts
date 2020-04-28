import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { UserDto } from '../dto/user.dto';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-loginpage',
  templateUrl: './loginpage.component.html',
  styleUrls: ['./loginpage.component.scss'],
})
export class LoginpageComponent implements OnInit {
  model: UserDto;

  @ViewChild('loginform') loginForm: NgForm;

  constructor(
    private authService: AuthService,
    private router: Router,
    private cookieService: CookieService
  ) {
    this.model = { login: '', password: '', email: '', role: '' };
  }

  ngOnInit(): void {}

  onSubmit() {
    this.model.login = this.loginForm.controls.login.value;
    this.model.password = this.loginForm.controls.password.value;
    this.authService.logToAccount(this.model).subscribe(
      (tmp) => this.router.navigate(['/home']),
      (err) => {
        alert('Wprowadzono błędne dane!'),
          this.loginForm.controls.login.setValue(''),
          this.loginForm.controls.password.setValue(''),
          console.log(err);
      }
    );
  }
}
