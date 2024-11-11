import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InventoryProductService {
  host = environment.apiUrl;
  constructor(private httpClient: HttpClient) {}

  public findStock(productId: number): Observable<HttpResponse<any>> {
    return this.httpClient.get(
      `${this.host}/api/inventory_product/findStock/${productId}`,
      { observe: 'response' }
    );
  }
}
