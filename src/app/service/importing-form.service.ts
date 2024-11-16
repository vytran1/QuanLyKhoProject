import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import {
  BehaviorSubject,
  catchError,
  combineLatest,
  map,
  Observable,
  of,
  switchMap,
  throwError,
} from 'rxjs';
import { ImportingFormDTO } from '../model/importing-form/importing-form-dto.model';

@Injectable({
  providedIn: 'root',
})
export class ImportingFormService {
  host = environment.apiUrl;

  importingformSubject = new BehaviorSubject<ImportingFormDTO[]>([]);
  pageNumSubject = new BehaviorSubject<number>(1);
  pageSizeSubject = new BehaviorSubject<number>(2);
  sortFieldSubject = new BehaviorSubject<string>('createdTime');
  sortDirSubject = new BehaviorSubject<string>('desc');
  totalPageSubject = new BehaviorSubject<number>(0);
  totalItemsSubject = new BehaviorSubject<number>(0);
  keyWordSubject = new BehaviorSubject<string>('');

  importingSubject$ = this.importingformSubject.asObservable();
  pageNum$ = this.pageNumSubject.asObservable();
  totalPage$ = this.totalPageSubject.asObservable();
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
            return this.search(pageNum, pageSize, sortField, sortDir, keyWord);
          } else {
            return this.listByPage(pageNum, pageSize, sortField, sortDir);
          }
        })
      )
      .subscribe({
        next: (response) => {
          if (response.body) {
            console.log(response);
            this.importingformSubject.next(response.body.dtos);
            this.totalPageSubject.next(response.body.totalPage);
            this.totalItemsSubject.next(response.body.totalItems);
          }
        },
        error: (error) => {
          console.error(error);
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
    return this.httpClient.get(`${this.host}/api/importing_form`, {
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
    return this.httpClient.get(`${this.host}/api/importing_form/search`, {
      observe: 'response',
      params: params,
    });
  }

  isExist(importingFormId: string): Observable<boolean> {
    return this.httpClient
      .get(`${this.host}/api/importing_form/exist/${importingFormId}`, {
        observe: 'response',
      })
      .pipe(
        map((response) => response.status === 200), // Return `true` if 200 OK
        catchError((error) => {
          if (error.status === 404) {
            return of(false); // Return `false` if 404 Not Found
          }
          return throwError(error); // Re-throw other errors
        })
      );
  }

  createImportingForm(importingForm: ImportingFormDTO) {
    return this.httpClient.post(
      `${this.host}/api/importing_form`,
      importingForm,
      { observe: 'response' }
    );
  }

  getImportingFormForDetailFunction(id: string): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/importing_form/${id}`, {
      observe: 'response',
    });
  }

  setPageNum(pageNum: number) {
    this.pageNumSubject.next(pageNum);
  }

  setPageSize(pageSize: number) {
    this.pageSizeSubject.next(pageSize);
  }

  setKeyWord(keyWord: string) {
    this.keyWordSubject.next(keyWord);
  }

  setSortFieldAndSortDir(sortField: string, sortDir: string) {
    this.sortFieldSubject.next(sortField);
    this.sortDirSubject.next(sortDir);
  }
}
