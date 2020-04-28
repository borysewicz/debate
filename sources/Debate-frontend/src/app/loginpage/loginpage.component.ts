import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDto } from '../dto/user.dto';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-loginpage',
  templateUrl: './loginpage.component.html',
  styleUrls: ['./loginpage.component.scss']
})
export class LoginpageComponent implements OnInit {
  model: UserDto;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.model = { login: '', password: '', email: '', role: '' };
  }

  onSubmit(){
    //todo log-in user logic
    console.log("DUPA");
  }
}
