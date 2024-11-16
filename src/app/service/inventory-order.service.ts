import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import {
  BehaviorSubject,
  combineLatest,
  map,
  Observable,
  switchMap,
} from 'rxjs';
import { OrderDTO } from '../model/order/order-dto.model';

@Injectable({
  providedIn: 'root',
})
export class InventoryOrderService {
  host = environment.apiUrl;
  inventoryOrdersSubject = new BehaviorSubject<OrderDTO[]>([]);
  inventoryOrders$ = this.inventoryOrdersSubject.asObservable();

  pageNumSubject = new BehaviorSubject<number>(1);
  pageSizeSubject = new BehaviorSubject<number>(2);
  sortFieldSubject = new BehaviorSubject<string>('createdTime');
  sortDirSubject = new BehaviorSubject<string>('asc');
  totalPageSubject = new BehaviorSubject<number>(0);
  totalItemsSubject = new BehaviorSubject<number>(0);
  keyWordSubject = new BehaviorSubject<string>('');
  fromSubject = new BehaviorSubject<string | null>(null);
  toSubject = new BehaviorSubject<string | null>(null);

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
      this.fromSubject,
      this.toSubject,
    ])
      .pipe(
        switchMap(
          ([pageNum, pageSize, sortField, sortDir, keyWord, from, to]) => {
            if (keyWord && from && to) {
              return this.searchByBothDateAndKeyWord(
                pageNum,
                pageSize,
                sortField,
                sortDir,
                keyWord,
                from,
                to
              );
            } else if (from && to) {
              return this.searchByDate(
                pageNum,
                pageSize,
                sortField,
                sortDir,
                from,
                to
              );
            } else if (keyWord) {
              return this.search(
                pageNum,
                pageSize,
                sortField,
                sortDir,
                keyWord
              );
            } else {
              return this.getAllOrderByPage(
                pageNum,
                pageSize,
                sortField,
                sortDir
              );
            }
          }
        )
      )
      .subscribe({
        next: (response) => {
          console.log('Call API InventoryOrder');
          console.log(response);
          if (response.body) {
            let body = response.body;
            this.inventoryOrdersSubject.next(body.orderDTOs);
            this.totalPageSubject.next(body.totalPage);
            this.totalItemsSubject.next(body.totalItems);
          }
        },
      });
  }

  getAllOrderByPage(
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
    return this.httpClient.get(`${this.host}/api/inventory_order`, {
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
    return this.httpClient.get(`${this.host}/api/inventory_order/search`, {
      observe: 'response',
      params: params,
    });
  }

  searchByDate(
    pageNum: number,
    pageSize: number,
    sortField: string,
    sortDir: string,
    from: string,
    to: string
  ): Observable<HttpResponse<any>> {
    let params = new HttpParams();
    params = params.append('pageNum', pageNum.toString());
    params = params.append('pageSize', pageSize.toString());
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
    (params = params.append('from', from)), (params = params.append('to', to));
    return this.httpClient.get(`${this.host}/api/inventory_order/search`, {
      observe: 'response',
      params: params,
    });
  }

  searchByBothDateAndKeyWord(
    pageNum: number,
    pageSize: number,
    sortField: string,
    sortDir: string,
    keyWord: string,
    from: string,
    to: string
  ): Observable<HttpResponse<any>> {
    let params = new HttpParams();
    params = params.append('pageNum', pageNum.toString());
    params = params.append('pageSize', pageSize.toString());
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
    params = params.append('keyWord', keyWord);
    (params = params.append('from', from)), (params = params.append('to', to));
    return this.httpClient.get(`${this.host}/api/inventory_order/search`, {
      observe: 'response',
      params: params,
    });
  }

  isExist(orderId: string): Observable<boolean> {
    return this.httpClient
      .get(`${this.host}/api/inventory_order/isExist/${orderId}`, {
        observe: 'response',
      })
      .pipe(map((response) => response.body as boolean));
  }

  createOrder(order: OrderDTO): Observable<HttpResponse<any>> {
    return this.httpClient.post(`${this.host}/api/inventory_order`, order, {
      observe: 'response',
    });
  }

  getDetailOrderById(orderId: string): Observable<HttpResponse<any>> {
    return this.httpClient.get(
      `${this.host}/api/inventory_order/detail/${orderId}`,
      {
        observe: 'response',
      }
    );
  }

  deleteOrder(orderId: string): Observable<HttpResponse<any>> {
    return this.httpClient.delete(
      `${this.host}/api/inventory_order/${orderId}`,
      {
        observe: 'response',
      }
    );
  }

  getOrderInfoForUpdateFunction(
    orderId: string
  ): Observable<HttpResponse<any>> {
    return this.httpClient.get(
      `${this.host}/api/inventory_order/update/${orderId}`,
      { observe: 'response' }
    );
  }

  updateOrder(order: OrderDTO): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/inventory_order/update`,
      order,
      {
        observe: 'response',
      }
    );
  }

  getOrdersWithoutImportingForm(): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/inventory_order/withoutIPF`, {
      observe: 'response',
    });
  }

  getOrderDetailsByOrderId(orderId: string): Observable<HttpResponse<any>> {
    return this.httpClient.get(
      `${this.host}/api/inventory_order/orderDetail/${orderId}`,
      { observe: 'response' }
    );
  }

  public setPageNum(pageNum: number) {
    this.pageNumSubject.next(pageNum);
  }

  public setPageSize(pageSize: number) {
    this.pageSizeSubject.next(pageSize);
  }

  public setSortFieldAndSortDir(sortField: string, sortDir: string) {
    this.sortFieldSubject.next(sortField);
    this.sortDirSubject.next(sortDir);
  }

  public setKeyWord(keyWord: string) {
    this.keyWordSubject.next(keyWord);
  }

  public setFromAndTo(from: string, to: string) {
    this.fromSubject.next(from);
    this.toSubject.next(to);
  }
}
