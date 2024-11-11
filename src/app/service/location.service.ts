import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  host = environment.apiUrl;
  constructor(private httpClient: HttpClient) {}

  public getCountries(): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/countries`, {
      observe: 'response',
    });
  }

  public getStates(countryId: number): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/states/${countryId}`, {
      observe: 'response',
    });
  }

  public getDistricts(stateId: number): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/districts/${stateId}`, {
      observe: 'response',
    });
  }
}
