import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { UserDto } from '../dto/user.dto';
import { UserService } from '../services/user.service';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
})
export class AccountComponent implements OnInit {
  userDto: UserDto;
  passwordCheck: string;
  oldPassword: string;
  model: UserDto;

  @ViewChild('regform') regform: NgForm;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) {
    this.userDto = { login: '', password: '', email: '', role: '' };
    this.model = { login: '', password: '', email: '', role: '' };
  }

  ngOnInit(): void {
    this.userService
      .getAccountByLogin(this.authService.userDto.login)
      .subscribe((user) => {
        (this.userDto = user),
          (this.userDto.password = this.authService.userDto.password);
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
