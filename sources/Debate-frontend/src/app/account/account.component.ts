import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { User } from '../dto/user.dto';
import { AuthService } from '../services/auth.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
})
export class AccountComponent implements OnInit {
  userDto: User;
  passwordCheck: string;
  oldPassword: string;
  model: User;

  @ViewChild('regform') regform: NgForm;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
      this.userDto = { login: '', password: '', email: '', role: '' };
      this.model = { login: '', password: '', email: '', role: '' };
      this.userService
      .getAccountByLogin(this.authService.userDto.login)
      .subscribe((user) => {
          this.userDto = user;
          this.userDto.password = this.authService.userDto.password;
      });
  }

  onSubmit() {
    if (!this.regform.form.valid) {
      return;
    }
    if (
      this.regform.controls.password.value ===
        this.regform.controls.passwordCheck.value &&
      this.userDto.password === this.regform.controls.oldPassword.value
    ) {
      this.model = this.userDto;
      this.model.password = this.regform.controls.password.value;
      this.userService.updateUserAccount(this.model).subscribe((tmp) => {
        this.authService.logOut(), this.router.navigate(['/home']);
      });
    } else {
      alert('Wprowadzono różne hasła!');
    }
  }
}
