import { Injectable } from '@angular/core';
import { UserLogIn } from '../dto/user-log-in';
import { UserDto } from '../dto/user.dto';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { map, tap } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private endpoint = environment.api;
  userLogin: UserLogIn;
  userDto: UserDto;
  private isLoggedIn = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private cookieService: CookieService) {}

  logToAccount(user: UserDto): Observable<UserLogIn> {
    return this.http
      .post<UserLogIn>(`${this.endpoint}/userLogin/logIn`, user)
      .pipe(
        tap((usrLogin) => {
          this.cookieService.set('token', usrLogin.token);
          localStorage.setItem('role', usrLogin.role);
          this.userLogin = usrLogin;
          this.isLoggedIn.next(true);
          this.userDto = user;
        })
      );
  }

  get isSignedIn(): Observable<boolean> {
    return this.isLoggedIn.asObservable();
  }

  logOut(): void {
    this.cookieService.delete('token',this.userLogin.token);
    localStorage.removeItem('role');
    this.isLoggedIn.next(false);
  }
}
