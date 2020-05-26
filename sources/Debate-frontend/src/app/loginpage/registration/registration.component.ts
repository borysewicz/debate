import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/dto/user.dto';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  model: User;
  passwordCheck: string;
  account: User;

  @ViewChild('regform') regform: NgForm;

  constructor(private userService: UserService, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.model = { login: '', password: '', email: '', role: 'USER' };
    this.account = { login: '', password: '', email: '', role: '' };
  }

  onSubmit() {
    if (!this.regform.form.valid) {
      return;
    }
    if (this.regform.controls.password.value === this.regform.controls.passwordCheck.value) {
      this.model.login = this.regform.controls.login.value;
      this.model.password = this.regform.controls.password.value;
      this.model.email = this.regform.controls.email.value;
      this.account.login = this.model.login;
      this.account.password = this.model.password;
      if(this.model.password.length < 8 && !this.containsNumber(this.model.password))
      {
        alert("Hasło musi mieć minimum 8 znaków i zawierać cyfrę!")
        return;
      }
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

  containsNumber(mystring:string): boolean { 

    var matches = mystring.match(/\d+/g)
    if (matches != null) { 
       return true;
    } else {
       return false;
    }
  } 
}
