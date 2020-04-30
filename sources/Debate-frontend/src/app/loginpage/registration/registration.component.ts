import { Component, OnInit, ViewChild } from '@angular/core';
import { UserDto } from 'src/app/dto/user.dto';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { UserLogIn } from 'src/app/dto/user-log-in';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  model: UserDto;
  passwordCheck: string;
  account: UserDto;

  @ViewChild('regform') regform: NgForm;

  constructor(private userService: UserService, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.model = {login: '', password: '', email: '', role: 'USER'};
    this.account = {login: '', password: '', email: '', role: ''};
  }

  onSubmit(){
    if (!this.regform.form.valid) {
      return;
    }
    if (this.regform.controls.password.value === this.regform.controls.passwordCheck.value) {
      this.model.login = this.regform.controls.login.value;
      this.model.password = this.regform.controls.password.value;
      this.model.email = this.regform.controls.email.value;
      this.account.login = this.model.login;
      this.account.password = this.model.password;
      this.userService.addUser(this.model).subscribe( 
        tmp => {
               this.authService.logToAccount(this.account);
                this.router.navigate(['/home']);
               },
        err => console.log(err),
    );
    } else {
      alert('Wprowadzono różne hasła!');
    }
  }
}
