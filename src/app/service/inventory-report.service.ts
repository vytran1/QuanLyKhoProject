import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployeeActivityReport } from '../model/report/employee-activity-report-request.model';
import { SummaryExportAndImportRequest } from '../model/report/summary-imp-and-exp-request.model';
import { DetailOfImportingOrExportingRequest } from '../model/report/detail-of-exporting-or-importing-request.model';

@Injectable({
  providedIn: 'root',
})
export class InventoryReportService {
  host = environment.apiUrl;

  constructor(private httpClient: HttpClient) {}

  reportListEmployees(): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/report/employees`, {
      observe: 'response',
    });
  }

  reportListProducts(): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/report/products`, {
      observe: 'response',
    });
  }

  reportListInventories(): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/report/inventories`, {
      observe: 'response',
    });
  }

  reportListOrderWithouImportingForm(): Observable<HttpResponse<any>> {
    return this.httpClient.get(`${this.host}/api/report/orderNoImport`, {
      observe: 'response',
    });
  }

  reportEmployeeActivity(
    requestBody: EmployeeActivityReport
  ): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/report/employeeActivities`,
      requestBody,
      { observe: 'response' }
    );
  }

  reportSummaryImpAndExp(
    requestBody: SummaryExportAndImportRequest
  ): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/report/summary`,
      requestBody,
      { observe: 'response' }
    );
  }

  reportDetailOfImportingOrExporting(
    requestBody: DetailOfImportingOrExportingRequest
  ): Observable<HttpResponse<any>> {
    return this.httpClient.post(
      `${this.host}/api/report/detailIPAndEP`,
      requestBody,
      { observe: 'response' }
    );
  }
}
