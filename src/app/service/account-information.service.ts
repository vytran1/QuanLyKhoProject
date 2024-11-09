import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Account } from '../model/account/account.model';
import { ChangePasswordRequest } from '../model/account/changepassword-request.model';

@Injectable({
  providedIn: 'root',
})
export class AccountInformationService {
  private host = environment.apiUrl;
  private accountInfoSubject = new BehaviorSubject<Account | null>(null);
  accountInfo$ = this.accountInfoSubject.asObservable();
  private profileImageSubject = new BehaviorSubject<string | null>(null);
  profileImage$ = this.profileImageSubject.asObservable();
  constructor(private httpClient: HttpClient) {}
  public getPersonalInfo(): Observable<HttpResponse<any>> {
    return this.httpClient.get<any>(`${this.host}/api/account`, {
      observe: 'response',
      withCredentials: true,
    });
  }

  public getPersonalImage(): Observable<Blob> {
    return this.httpClient.get(`${this.host}/api/account/profileImage`, {
      responseType: 'blob',
    });
  }

  public updatePersonalInformation(
    account: Account
  ): Observable<HttpResponse<any>> {
    return this.httpClient.post(`${this.host}/api/account`, account, {
      observe: 'response',
    });
  }

  public updatePassword(
    request: ChangePasswordRequest
  ): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/account/changePassword`,
      request,
      { observe: 'response' }
    );
  }

  public updatePersonalImage(file: File): Observable<HttpResponse<any>> {
    let formData = new FormData();
    formData.append('photo', file);
    return this.httpClient.post(
      `${this.host}/api/account/updateImage`,
      formData,
      { observe: 'response' }
    );
  }

  public setAccountInfo(accountInfo: Account) {
    this.accountInfoSubject.next(accountInfo);
  }

  public getAccountInfo() {
    return this.accountInfoSubject.value;
  }

  public setProfileImage(imageUrl: string) {
    this.profileImageSubject.next(imageUrl);
  }

  public getProfileImage() {
    return this.profileImageSubject.value;
  }
}
