import {
  HttpEvent,
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
} from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthenticationService } from '../service/authentication.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<HttpEvent<any>> => {
  const authService = inject(AuthenticationService);
  const jwtHelper = new JwtHelperService();
  const router = inject(Router);
  if (
    req.url.includes(`${authService.host}/api/auth/token`) ||
    req.url.includes(`${authService.host}/api/forgot_password`) ||
    req.url.includes(`${authService.host}/api/reset_password`)
  ) {
    return next(req);
  }

  authService.loadToken();
  const jwtToken = authService.getToken();
  //console.log('JWT Token:', jwtToken); // Debugging log
  if (jwtToken) {
    if (jwtHelper.isTokenExpired(jwtToken)) {
      authService.logout();
      router.navigate(['/login']);
      return throwError(() => new Error('Token expired'));
    }

    const requestClone = req.clone({
      setHeaders: { Authorization: `Bearer ${jwtToken}` },
    });
    //console.log('Request with token:', requestClone);
    return next(requestClone).pipe(
      catchError((error) => {
        if (error.status === 401) {
          authService.logout();
          router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  } else {
    return next(req);
  }
};
