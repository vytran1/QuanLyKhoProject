import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthRequest } from '../model/auth/auth-request.model';
import { JwtHelperService } from '@auth0/angular-jwt';
import { InventoryUser } from '../model/inventory-user/inventory-user.model';

@Injectable({
  providedIn: 'root',
})
export class InventoryUserService {
  private host = environment.apiUrl;

  constructor(private httpClient: HttpClient) {}
  //List All By Page
  public getAllInventoryUsers(): Observable<any> {
    let params = new HttpParams()
      .set('pageNum', 1)
      .set('pageSize', 2)
      .set('sortField', 'firstName')
      .set('sortDir', 'asc');
    return this.httpClient.get<any>(
      `${this.host}/api/v1/inventory_users/findByPage`,
      {
        params,
      }
    );
  }
  //Add User
  public addUser(user: InventoryUser): Observable<any> {
    return this.httpClient.post(
      `${this.host}/api/v1/inventory_user/createUser`,
      user,
      { observe: 'response' }
    );
  }

  //Update
  public updateUser(user: InventoryUser) {
    return this.httpClient.post(
      `${this.host}/api/v1/inventory_user/updateUser`,
      user,
      { observe: 'response' }
    );
  }

  //Get By Id
  public getUserById(userId: string): Observable<any> {
    return this.httpClient.get(
      `${this.host}/api/v1/inventory_user/findByUserID/${userId}`,
      { observe: 'response' }
    );
  }

  //Delete By Id
  public deleteUserById(userId: string): Observable<any> {
    return this.httpClient.delete(
      `${this.host}/api/v1/inventory_user/deleteUser/${userId}`,
      { observe: 'response' }
    );
  }

  //Export Through Excel
  public exportUsersExcel(): Observable<any> {
    return this.httpClient.get(`${this.host}/api/v1/inventory_user/excel`, {
      observe: 'response',
    });
  }

  //Create Multiple user by excel file
  public addMultipleUsers(file: File) {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.httpClient.post(
      `${this.host}/api/v1/inventory_user/createByExcel`,
      formData,
      { observe: 'response' }
    );
  }
}
