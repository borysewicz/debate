import { HttpClient } from '@angular/common/http';
import { UserDto } from './../dto/user.dto';
import { from, Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  userRole = 'USER';
  private endpoint = 'http://localhost:8080/api/user';
  userDto: UserDto;

  constructor(private http: HttpClient) {}

  addUser(user: UserDto): Observable<UserDto> {
    user.role = this.userRole;
    return this.http.post<UserDto>(`${this.endpoint}/add`, user);
  }

  logToAccount(user: UserDto): Observable<UserDto> {
    //todo return http method to log-in user when endpoint ready
    this.userDto = user;
    user.role = this.userRole;
    return;
  }

  getAccountByLogin(login: string): Observable<UserDto> {
    return this.http.get<UserDto>(`${this.endpoint}/login/` + login);
  }

  updateUserAccount(user: UserDto): Observable<UserDto> {
    return this.http.put<UserDto>(`${this.endpoint}/changePassword`,user);
  }
}
