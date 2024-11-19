import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthRequest } from '../model/auth/auth-request.model';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ForgotPasswordRequest } from '../model/auth/forgot-password-request.model';
import { ResetPasswordRequest } from '../model/auth/reset-password-request.model';
import { Role } from '../../environments/role.enum';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  public host = environment.apiUrl;
  private jwtToken: any = null;
  private loggedInUsername = null;
  private jwtHelper = new JwtHelperService();
  private role = null;

  constructor(private httpClient: HttpClient) {}

  public login(authRequest: AuthRequest): Observable<HttpResponse<any>> {
    return this.httpClient
      .post<HttpResponse<any> | HttpErrorResponse>(
        `${this.host}/api/auth/token`,
        authRequest,
        { observe: 'response' }
      )
      .pipe(
        catchError((error: HttpErrorResponse) => {
          return throwError(() => error);
        })
      );
  }

  public forgotPassword(
    request: ForgotPasswordRequest
  ): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.httpClient.post<HttpResponse<any> | HttpErrorResponse>(
      `${this.host}/api/forgot_password`,
      request,
      { observe: 'response' }
    );
  }

  public resetPassword(
    request: ResetPasswordRequest,
    token: string
  ): Observable<HttpResponse<any>> {
    const params = new HttpParams().set('token', token);
    return this.httpClient.post(`${this.host}/api/reset_password`, request, {
      observe: 'response',
      params: params,
    });
  }

  public logout(): void {
    this.jwtToken = null;
    this.loggedInUsername = null;
    this.role = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  }

  public saveToken(token: string) {
    this.jwtToken = token;
    this.setRole();
    localStorage.setItem('token', token);
  }

  public loadToken() {
    this.jwtToken = localStorage.getItem('token');
    if (this.jwtToken) {
      this.setRole();
    }
    //console.log('Get token: ' + this.jwtToken);
  }

  public getToken() {
    return this.jwtToken;
  }

  public isLogged(): boolean {
    this.loadToken();
    if (this.jwtToken != null && this.jwtToken !== '') {
      if (this.jwtHelper.decodeToken(this.jwtToken).sub != null || '') {
        if (!this.jwtHelper.isTokenExpired(this.jwtToken)) {
          this.loggedInUsername = this.jwtHelper.decodeToken(this.jwtToken).sub;
          //console.log('User loggin with name is:' + this.loggedInUsername);

          return true;
        }
      }
    }
    this.logout();
    return false;
  }

  public setRole(): any {
    const decodedToken = this.jwtHelper.decodeToken(this.jwtToken);
    //console.log(decodedToken);

    this.role = decodedToken.role;
    //console.log('Role Setup: ', this.role);

    //console.log(this.jwtHelper.decodeToken(this.jwtToken).sub);

    return this.role !== null ? this.role : null;
  }

  public getUsernameFromToken(): string | null {
    const token = this.getToken();
    if (token) {
      const decodedToken = this.jwtHelper.decodeToken(token);
      const subject = decodedToken.sub; // Assuming sub contains the value "Nguyễn Trần Vỹ,N21DCVT128,vy.tn171003@gmail.com"
      if (subject) {
        const username = subject.split(',')[0]; // Take the first part before the comma
        // console.log('Username extracted:', username);
        return username;
      }
    }
    return null; // Return null if no token or subject found
  }

  isCompany() {
    //console.log('Compare Role Company: ', this.role);
    return this.role === Role.COMPANY;
  }

  isEmployee() {
    //console.log(this.role);
    return this.role === Role.EMPLOYEE;
  }
}
