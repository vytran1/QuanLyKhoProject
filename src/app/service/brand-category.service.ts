import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BrandCategoryService {
  host = environment.apiUrl;
  constructor(private httpClient: HttpClient) {}

  public getBrands(): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/brands`, {
      observe: 'response',
    });
  }

  public getCategories(brandId: number): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/categories/${brandId}`, {
      observe: 'response',
    });
  }
}
