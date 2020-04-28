import { Component, OnInit, ViewChild } from '@angular/core';
import { UserDto } from 'src/app/dto/user.dto';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  model: UserDto;
  passwordCheck: string;

  @ViewChild('regform') regform: NgForm;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
      this.model = { login: '', password: '', email: '', role: 'USER' };
  }

  onSubmit(){
    if (!this.regform.form.valid){
      return;
    }
    if (this.regform.controls.password.value === this.regform.controls.passwordCheck.value){
      this.model.login = this.regform.controls.login.value;
      this.model.password = this.regform.controls.password.value;
      this.model.email = this.regform.controls.email.value;
      // todo when successful registration then log-in user to new account
      this.userService.addUser(this.model).subscribe(
        res => this.router.navigate(['/home']), 
        err => console.log(err)
    );
    } else {
      alert('Wprowadzono różne hasła!');
    }
  }
}
