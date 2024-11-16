import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, combineLatest, Observable, switchMap } from 'rxjs';
import { Product } from '../model/product/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductForOrderService {
  host = environment.apiUrl;

  productSubject = new BehaviorSubject<Product[]>([]);
  private pageNumSubject = new BehaviorSubject<number>(1);
  private pageSizeSubject = new BehaviorSubject<number>(2);
  private sortFieldSubject = new BehaviorSubject<string>('id');
  private sortDirSubject = new BehaviorSubject<string>('asc');
  private totalPageSubject = new BehaviorSubject<number>(0);
  private totalItemsSubject = new BehaviorSubject<number>(0);
  private keyWordSubject = new BehaviorSubject<string>('');

  product$ = this.productSubject.asObservable();
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
          if (response.body) {
            let body = response.body;
            this.productSubject.next(body.productDTOs);
            this.totalItemsSubject.next(body.totalItems);
            this.totalPageSubject.next(body.totalPage);
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
    return this.httpClient.get(`${this.host}/api/v1/products/all`, {
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
  ): Observable<HttpResponse<any>> {
    let params = new HttpParams();
    params = params.append('pageNum', pageNum.toString());
    params = params.append('pageSize', pageSize.toString());
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
    params = params.append('keyWord', keyWord);
    return this.httpClient.get(`${this.host}/api/v1/products/search`, {
      observe: 'response',
      params: params,
    });
  }

  public setPageNum(pageNum: number) {
    this.pageNumSubject.next(pageNum);
  }

  public setKeyWord(keyWord: string) {
    this.keyWordSubject.next(keyWord);
  }
}
