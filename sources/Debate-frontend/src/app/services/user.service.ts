import { HttpClient } from '@angular/common/http';
import { UserDto } from './../dto/user.dto';
import { from, Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
  })
export class UserService {
    userRole = 'USER';
    private endpoint = 'http://localhost:8080/api/user';

    constructor(private http: HttpClient) {}

    addUser(user: UserDto):Observable<UserDto>{
        user.role = this.userRole;
        return this.http.post<UserDto>(`${this.endpoint}/add`,user);
    }

    logToAccount(user: UserDto):Observable<UserDto>{
        //todo return http method to log-in user when endpoint ready
        user.role = this.userRole;
        return;
    }
}
