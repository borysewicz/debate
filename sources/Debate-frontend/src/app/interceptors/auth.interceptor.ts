import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { tap, catchError } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    let currentUser = this.authService.userLogin;
    if (currentUser && currentUser.token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${currentUser.token}`,
        },
      });
    }
    return next.handle(request).pipe(
      tap((tmp: HttpEvent<any>) => {
        if (tmp instanceof HttpResponse) {
          if (tmp.body && tmp.body.success && tmp.headers.has('New_Token')) {
            this.authService.userLogin.token = tmp.headers.get('New_Token');
          }
        }
      }),
      catchError((err: HttpErrorResponse) => {
        if (err && err.status === 401) {
          console.log('DUPA');
        }
        return throwError(err);
      })
    );
  }
}
