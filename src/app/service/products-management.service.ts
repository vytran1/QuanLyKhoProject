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
import { Product } from '../model/product/productsManagement.model';

@Injectable({
  providedIn: 'root',
})
export class ProductsManagementService {
  host: string = environment.apiUrl;
  productsSubject = new BehaviorSubject<Product[]>([]);
  products$ = this.productsSubject.asObservable();

  private pageNumSubject = new BehaviorSubject<number>(1);
  private pageSizeSubject = new BehaviorSubject<number>(2);
  private sortFieldSubject = new BehaviorSubject<string>('name');
  private sortDirSubject = new BehaviorSubject<string>('asc');
  private totalPageSubject = new BehaviorSubject<number>(0);
  private totalItemsSubject = new BehaviorSubject<number>(0);
  private keyWordSubject = new BehaviorSubject<string>('');
  //private categoryIdSubject = new BehaviorSubject<number | null>(null);

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
      //this.categoryIdSubject,
    ])
      .pipe(
        switchMap(([pageNum, pageSize, sortField, sortDir, keyWord]) => {
          if (keyWord) {
            return this.searchProducts(pageNum, pageSize, sortField, sortDir, keyWord);
          } else {
            return this.getAllProductsByPage(
              pageNum,
              pageSize,
              sortField,
              sortDir,
         
            );
          }
        })
      )
      .subscribe({
        next: (response) => {
          if (response.body) {
            console.log('Call Product API');

            let bodyRequest = response.body;
            this.productsSubject.next(bodyRequest.productDTOs);
            this.totalPageSubject.next(bodyRequest.totalPage);
            this.totalItemsSubject.next(bodyRequest.totalItems);
            //this.pageNumSubject.next(bodyRequest.pageNum);
          }
        },
      });
  }

  public getAllProductsByPage(
    pageNum: number,
    pageSize: number,
    sortField: string,
    sortDir: string,
    
  ): Observable<HttpResponse<any>> {
    let params = new HttpParams();
    params = params.append('pageNum', pageNum.toString());
    params = params.append('pageSize', pageSize.toString());
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
   
    return this.httpClient.get(`${this.host}/api/v1/products/findAll`, {
      observe: 'response',
      params: params,
    });
  }

  public searchProducts(
    pageNum: number,
    pageSize: number,
    sortField: string,
    sortDir: string,
    keyWord: string,
  ): Observable<any> {
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

  public deleteProduct(productId: any): Observable<HttpResponse<any>> {
    return this.httpClient.delete(
      `${this.host}/api/v1/products/deleteProduct/${productId}`,
      { observe: 'response' }
    );
  }

  public createProduct(product: Product): Observable<HttpResponse<any>> {
    return this.httpClient.post(`${this.host}/api/v1/products/createProduct`, product, {
      observe: 'response',
    });
  }

  public getProductById(productId: number): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/v1/products/findByProductID/${productId}`, {
      observe: 'response',
    });
  }

  public updateProduct(
    product: Product
  ): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/v1/products/updateProduct`,
      product,
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
    return this.getAllProductsByPage(1, 2, 'name', 'asc');
  }
}
