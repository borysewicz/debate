import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

import { User } from '../dto/user.dto';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  userRole = 'USER';
  private readonly endpoint = environment.api + '/user';
  userDto: User;

  constructor(private http: HttpClient) {}

  addUser(user: User): Observable<User> {
    user.role = this.userRole;
    return this.http.post<User>(`${this.endpoint}/add`, user);
  }

  getAccountByLogin(login: string): Observable<User> {
    return this.http.get<User>(`${this.endpoint}/login/` + login);
  }

  updateUserAccount(user: User): Observable<User> {
    return this.http.put<User>(`${this.endpoint}/changePassword`, user);
  }
}
