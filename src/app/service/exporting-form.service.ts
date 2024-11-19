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
import { ExportingFormDTO } from '../model/exporting_form/exporting-form-dto.model';

@Injectable({
  providedIn: 'root',
})
export class ExportingFormService {
  host = environment.apiUrl;

  exportingFormSubject = new BehaviorSubject<ExportingFormDTO[]>([]);
  pageNumSubject = new BehaviorSubject<number>(1);
  pageSizeSubject = new BehaviorSubject<number>(2);
  sortFieldSubject = new BehaviorSubject<string>('createdTime');
  sortDirSubject = new BehaviorSubject<string>('desc');
  totalItemsSubject = new BehaviorSubject<number>(0);
  totalPageSubject = new BehaviorSubject<number>(0);
  keyWordSubject = new BehaviorSubject<string>('');

  exportingForm$ = this.exportingFormSubject.asObservable();
  pageNum$ = this.pageNumSubject.asObservable();
  totalItems$ = this.totalItemsSubject.asObservable();
  totalPage$ = this.totalPageSubject.asObservable();

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
          //console.log('Importing Form');
          //console.log(response);
          this.exportingFormSubject.next(response.body.dtos);
          this.totalItemsSubject.next(response.body.totalItems);
          this.totalPageSubject.next(response.body.totalPage);
        },
        error: (error) => {
          console.error('Error:', error);
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
    (params = params.append('pageNum', pageNum.toString())),
      (params = params.append('pageSize', pageSize.toString())),
      (params = params.append('sortField', sortField)),
      (params = params.append('sortDir', sortDir));
    return this.httpClient.get(`${this.host}/api/exporting_forms`, {
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
    (params = params.append('pageNum', pageNum.toString())),
      (params = params.append('pageSize', pageSize.toString())),
      (params = params.append('sortField', sortField)),
      (params = params.append('sortDir', sortDir));
    params = params.append('keyWord', keyWord);
    return this.httpClient.get(`${this.host}/api/exporting_forms/search`, {
      observe: 'response',
      params: params,
    });
  }

  isExist(exportingFormId: string): Observable<boolean> {
    return this.httpClient
      .get<{ isExist: boolean }>(
        `${this.host}/api/exporting_forms/exist/${exportingFormId}`
      )
      .pipe(map((response) => response.isExist));
  }

  createExportingForm(
    exportingForm: ExportingFormDTO
  ): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/exporting_forms`,
      exportingForm,
      { observe: 'response' }
    );
  }

  getFullDetailOfOneExportingForm(
    exportingFormId: string
  ): Observable<HttpResponse<any>> {
    return this.httpClient.get(
      `${this.host}/api/exporting_forms/${exportingFormId}`,
      { observe: 'response' }
    );
  }

  setPageNum(pageNum: number) {
    this.pageNumSubject.next(pageNum);
  }

  setPageSize(pageSize: number) {
    this.pageSizeSubject.next(pageSize);
  }

  setSortFieldAndSortDir(sortField: string, sortDir: string) {
    this.sortFieldSubject.next(sortField);
    this.sortDirSubject.next(sortDir);
  }

  setKeyWord(keyWord: string) {
    this.keyWordSubject.next(keyWord);
  }
}
