import { HttpClient } from '@angular/common/http';
import { UserDto } from './../dto/user.dto';
import { from, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  userRole = 'USER';
  private readonly endpoint = environment.api + '/user';
  userDto: UserDto;

  constructor(private http: HttpClient) {}

  addUser(user: UserDto): Observable<UserDto> {
    user.role = this.userRole;
    return this.http.post<UserDto>(`${this.endpoint}/add`, user);
  }

  getAccountByLogin(login: string): Observable<UserDto> {
    return this.http.get<UserDto>(`${this.endpoint}/login/` + login);
  }

  updateUserAccount(user: UserDto): Observable<UserDto> {
    return this.http.put<UserDto>(`${this.endpoint}/changePassword`,user);
  }
}
