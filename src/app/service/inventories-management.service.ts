import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {
  BehaviorSubject,
  combineLatest,
  map,
  Observable,
  switchMap,
} from 'rxjs';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Inventory } from '../model/inventories/inventories.model';

@Injectable({
  providedIn: 'root',
})
export class InventoriesManagementService {
  host: string = environment.apiUrl;
  inventoriesSubject = new BehaviorSubject<Inventory[]>([]);
  inventories$ = this.inventoriesSubject.asObservable();

  private pageNumSubject = new BehaviorSubject<number>(1);
  private pageSizeSubject = new BehaviorSubject<number>(2);
  private sortFieldSubject = new BehaviorSubject<string>('inventoryName');
  private sortDirSubject = new BehaviorSubject<string>('asc');
  private totalPageSubject = new BehaviorSubject<number>(0);
  private totalItemsSubject = new BehaviorSubject<number>(0);
  private keyWordSubject = new BehaviorSubject<string>('');

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
            return this.getInventoriesByPage(
              pageNum,
              pageSize,
              sortField,
              sortDir
            );
          }
        })
      )
      .subscribe({
        next: (response) => {
          if (response.body) {
            console.log('Call Inventory API');

            let bodyRequest = response.body;
            this.inventoriesSubject.next(bodyRequest.inventoryDTOs);
            this.totalPageSubject.next(bodyRequest.totalPage);
            this.totalItemsSubject.next(bodyRequest.totalItems);
            //this.pageNumSubject.next(bodyRequest.pageNum);
          }
        },
      });
  }

  public getInventoriesByPage(
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
    return this.httpClient.get(`${this.host}/api/inventories`, {
      observe: 'response',
      params: params,
    });
  }

  public search(
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
    return this.httpClient.get(`${this.host}/api/inventories/search`, {
      observe: 'response',
      params: params,
    });
  }

  public delete(inventoryId: any): Observable<HttpResponse<any>> {
    return this.httpClient.delete(
      `${this.host}/api/inventories/${inventoryId}`,
      { observe: 'response' }
    );
  }

  public isExist(inventoryId: any): Observable<boolean> {
    return this.httpClient
      .get(`${this.host}/api/inventories/isExist/${inventoryId}`, {
        observe: 'response',
      })
      .pipe(map((response) => response.body as boolean));
  }

  public addInventory(inventory: Inventory): Observable<HttpResponse<any>> {
    return this.httpClient.post(`${this.host}/api/inventories`, inventory, {
      observe: 'response',
    });
  }

  public getInventoryById(inventoryId: any): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/inventories/${inventoryId}`, {
      observe: 'response',
    });
  }

  public updateInventory(
    requestBody: Inventory
  ): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/inventories/update`,
      requestBody,
      { observe: 'response' }
    );
  }

  public setPageNum(pageNum: any) {
    this.pageNumSubject.next(pageNum);
  }

  public setPageSize(pageSize: any) {
    this.pageSizeSubject.next(pageSize);
  }

  public setSortField(sortField: any) {
    this.sortFieldSubject.next(sortField);
  }

  public setSortDir(sortDir: any) {
    this.sortDirSubject.next(sortDir);
  }

  public setSortFieldAndSortDir(sortField: string, sortDir: string) {
    this.sortFieldSubject.next(sortField);
    this.sortDirSubject.next(sortDir);
  }

  public setKeyWord(keyWord: string) {
    this.keyWordSubject.next(keyWord);
  }

  public getFirstPage() {
    return this.getInventoriesByPage(1, 2, 'inventoryName', 'asc');
  }
}
