import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {
  BehaviorSubject,
  combineLatest,
  map,
  Observable,
  switchMap,
} from 'rxjs';
import { InventoryUser } from '../model/inventory-user/inventory-user.model';

@Injectable({
  providedIn: 'root',
})
export class InventoryUserManagementService {
  private host = environment.apiUrl;
  private userSubject = new BehaviorSubject<InventoryUser[]>([]);
  users$ = this.userSubject.asObservable();

  private pageNumSubject = new BehaviorSubject<number>(1);
  private pageSizeSubject = new BehaviorSubject<number>(2);
  private sortFieldSubject = new BehaviorSubject<string>('userId');
  private sortDirSubject = new BehaviorSubject<string>('asc');
  private keyWordSubject = new BehaviorSubject<string>('');

  private totalPagesSubject = new BehaviorSubject<number>(0);
  totalPage$ = this.totalPagesSubject.asObservable();
  private totalItemsSubject = new BehaviorSubject<number>(0);
  totalItem$ = this.totalItemsSubject.asObservable();

  constructor(private httpClient: HttpClient) {
    combineLatest([
      this.pageNumSubject,
      this.pageSizeSubject,
      this.sortFieldSubject,
      this.sortDirSubject,
      this.keyWordSubject,
    ])
      .pipe(
        switchMap(([pageNum, pageSize, sortField, sortDir, keyWord]) => {
          if (keyWord) {
            return this.search(keyWord, pageNum, pageSize, sortField, sortDir);
          } else {
            return this.listByPage(pageNum, pageSize, sortField, sortDir);
          }
        })
      )
      .subscribe((response) => {
        console.log('Response Find By Page');
        console.log(response);
        const body = response.body;
        if (body) {
          this.userSubject.next(body.inventoryUsers);
          this.totalPagesSubject.next(body.totalPage);
          this.totalItemsSubject.next(body.totalItems);
        }
      });
  }

  public listByPage(
    pageNum: number,
    pageSize: number,
    sortField: string,
    sortDir: string
  ): Observable<HttpResponse<any>> {
    let params = new HttpParams();
    params = params.append('pageNum', pageNum.toString());
    params = params.append('pageSize', pageSize.toString());
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
    return this.httpClient.get(
      `${this.host}/api/v1/inventory_users/findByPage`,
      { observe: 'response', params: params }
    );
  }

  public search(
    keyWord: string,
    pageNum: number,
    pageSize: number,
    sortField: string,
    sortDir: string
  ): Observable<HttpResponse<any>> {
    let params = new HttpParams();
    params = params.append('pageNum', pageNum.toString());
    params = params.append('pageSize', pageSize.toString());
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
    params = params.append('keyWord', keyWord);
    return this.httpClient.get(`${this.host}/api/v1/inventory_users/search`, {
      observe: 'response',
      params: params,
    });
  }

  public getProfileImage(userId: string): Observable<Blob> {
    return this.httpClient.get(
      `${this.host}/api/v1/inventory_users/profileImage/${userId}`,
      { responseType: 'blob' }
    );
  }

  public deleteUser(userId: any): Observable<HttpResponse<any>> {
    return this.httpClient.delete(
      `${this.host}/api/v1/inventory_users/deleteUser/${userId}`,
      { observe: 'response' }
    );
  }

  public getFirstPage(): Observable<HttpResponse<any>> {
    return this.listByPage(1, 2, 'userId', 'asc');
  }

  public checkUserIdDuplicate(userId: string): Observable<boolean> {
    return this.httpClient
      .get(`${this.host}/api/v1/inventory_users/checkUserId/${userId}`, {
        observe: 'response',
      })
      .pipe(map((response) => response.body as boolean));
  }

  public checkEmailDuplicate(email: string): Observable<boolean> {
    return this.httpClient
      .get(`${this.host}/api/v1/inventory_users/checkEmail/${email}`, {
        observe: 'response',
      })
      .pipe(map((response) => response.body as boolean));
  }

  public createUser(newUser: InventoryUser): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/v1/inventory_users/createUser`,
      newUser,
      { observe: 'response' }
    );
  }

  public updateUser(updateUser: InventoryUser): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/v1/inventory_users/updateUser`,
      updateUser,
      { observe: 'response' }
    );
  }

  public getUserById(userId: string): Observable<HttpResponse<any>> {
    return this.httpClient.get(
      `${this.host}/api/v1/inventory_users/findByUserID/${userId}`,
      { observe: 'response' }
    );
  }

  public checkIdentityNumberDuplicate(
    identityNumber: string
  ): Observable<boolean> {
    return this.httpClient
      .get(
        `${this.host}/api/v1/inventory_users/checkIdentityNumber/${identityNumber}`,
        {
          observe: 'response',
        }
      )
      .pipe(map((response) => response.body as boolean));
  }

  setPageNum(pageNum: number) {
    this.pageNumSubject.next(pageNum);
  }
  setPageSize(pageSize: number) {
    this.pageSizeSubject.next(pageSize);
  }
  setSortField(sortField: string) {
    this.sortFieldSubject.next(sortField);
  }
  setSortDir(sortDir: string) {
    this.sortDirSubject.next(sortDir);
  }
  setKeyWord(keyWord: string) {
    this.keyWordSubject.next(keyWord);
  }
  setSortFieldAndDir(sortField: string, sortDir: string) {
    this.sortFieldSubject.next(sortField);
    this.sortDirSubject.next(sortDir);
  }
}
