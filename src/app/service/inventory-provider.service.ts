import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, combineLatest, Observable, switchMap } from 'rxjs';
import { InventoryProvider } from '../model/provider/inventory_provider.model';

@Injectable({
  providedIn: 'root',
})
export class InventoryProviderService {
  host = environment.apiUrl;
  inventoryProvidersSubject = new BehaviorSubject<InventoryProvider[]>([]);
  inventoryProviders$ = this.inventoryProvidersSubject.asObservable();

  pageNumSubject = new BehaviorSubject<number>(1);
  pageSizeSubject = new BehaviorSubject<number>(2);
  sortFieldSubject = new BehaviorSubject<string>('providerId');
  sortDirSubject = new BehaviorSubject<string>('asc');
  totalPageSubject = new BehaviorSubject<number>(0);
  totalItemsSubject = new BehaviorSubject<number>(0);
  keyWordSubject = new BehaviorSubject<string>('');

  pageNum$ = this.pageNumSubject.asObservable();
  totalPage$ = this.totalPageSubject.asObservable();
  totalItems$ = this.totalItemsSubject.asObservable();

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
            return this.search(pageNum, pageSize, sortField, sortDir, keyWord);
          } else {
            return this.listByPage(pageNum, pageSize, sortField, sortDir);
          }
        })
      )
      .subscribe({
        next: (response) => {
          console.log('Call API Provider');
          if (response.body) {
            let body = response.body;
            this.inventoryProvidersSubject.next(body.providers);
            this.totalPageSubject.next(body.totalPage);
            this.totalItemsSubject.next(body.totalItems);
          }
        },
      });
  }

  listByPage(
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
    return this.httpClient.get(`${this.host}/api/providers`, {
      observe: 'response',
      params: params,
    });
  }

  search(
    pageNum: number,
    pageSize: number,
    sortField: string,
    sortDir: string,
    keyWord: string
  ) {
    let params = new HttpParams();
    params = params.append('pageNum', pageNum.toString());
    params = params.append('pageSize', pageSize.toString());
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
    params = params.append('keyWord', keyWord);
    return this.httpClient.get(`${this.host}/api/providers/search`, {
      observe: 'response',
      params: params,
    });
  }

  isUnique(providerId: number | null, email: string): Observable<boolean> {
    let params = new HttpParams();
    params = params.append('providerEmail', email);
    if (providerId) {
      params = params.append('providerId', providerId.toString());
    }
    return this.httpClient.get<boolean>(
      `${this.host}/api/providers/checkunique`,
      { params: params }
    );
  }

  createProvider(provider: InventoryProvider): Observable<HttpResponse<any>> {
    return this.httpClient.post(`${this.host}/api/providers`, provider, {
      observe: 'response',
    });
  }

  editProvider(
    providerId: number,
    provider: InventoryProvider
  ): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/providers/update/${providerId}`,
      provider,
      { observe: 'response' }
    );
  }

  getProviderById(providerId: number): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/providers/${providerId}`, {
      observe: 'response',
    });
  }

  deleteProviderById(providerId: number): Observable<HttpResponse<any>> {
    return this.httpClient.delete(`${this.host}/api/providers/${providerId}`, {
      observe: 'response',
    });
  }

  setPageNum(value: number) {
    this.pageNumSubject.next(value);
  }

  setPageSize(value: number) {
    this.pageSizeSubject.next(value);
  }

  setSortFieldAndSortDir(sortField: string, sortDir: string) {
    this.sortFieldSubject.next(sortField);
    this.sortDirSubject.next(sortDir);
  }

  setKeyWord(value: string) {
    this.keyWordSubject.next(value);
  }
}
