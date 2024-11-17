import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { InventoryReportService } from '../../service/inventory-report.service';
import { MessageModalComponent } from '../../message-modal/message-modal.component';
import { EmployeeActivityReportRequestFormComponent } from '../../sub-component/employee-activity-report-request-form/employee-activity-report-request-form.component';
import { SummaryImpAndExpReportFormComponent } from '../../sub-component/summary-imp-and-exp-report-form/summary-imp-and-exp-report-form.component';
import { DetailOfExporingOrImportingReportFormComponent } from '../../sub-component/detail-of-exporing-or-importing-report-form/detail-of-exporing-or-importing-report-form.component';

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [
    MessageModalComponent,
    EmployeeActivityReportRequestFormComponent,
    SummaryImpAndExpReportFormComponent,
    DetailOfExporingOrImportingReportFormComponent,
  ],
  templateUrl: './report.component.html',
  styleUrl: './report.component.css',
})
export class ReportComponent implements OnDestroy {
  subscriptions: Subscription[] = [];
  //Modal Response
  showModalResponse = false;
  message = '';
  type: 'success' | 'error' = 'success';
  constructor(private reportService: InventoryReportService) {}
  //Employee Activity
  showModalEmployeeActivity = false;

  //Detail Of Exporting Or Importing
  showModalExportingOrImporting = false;

  //Summary Exporting And Importing
  showModalSummaryReport = false;

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  reportListEmployees() {
    this.subscriptions.push(
      this.reportService.reportListEmployees().subscribe({
        next: (response) => {
          console.log(response);
          this.message =
            'Báo cáo đã được tạo và lưu vào folder D\\JasperReportHolder\\employeeReport. Hãy kiểm tra ';
          this.type = 'success';
          this.showModalResponse = true;
        },
        error: (error) => {
          console.log(error);
          this.message = error.error;
          this.type = 'error';
          this.showModalResponse = true;
        },
      })
    );
  }

  reportListProducts() {
    this.subscriptions.push(
      this.reportService.reportListProducts().subscribe({
        next: (response) => {
          console.log(response);
          this.message =
            'Báo cáo đã được tạo và lưu vào folder D\\JasperReportHolder\\ReportProducts. Hãy kiểm tra ';
          this.type = 'success';
          this.showModalResponse = true;
        },
        error: (error) => {
          console.log(error);
          this.message = error.error;
          this.type = 'error';
          this.showModalResponse = true;
        },
      })
    );
  }

  reportListInventories() {
    this.subscriptions.push(
      this.reportService.reportListInventories().subscribe({
        next: (response) => {
          console.log(response);
          this.message =
            'Báo cáo đã được tạo và lưu vào folder D\\JasperReportHolder\\ReportProducts.Hãy kiểm tra thư mục trên máy tính của bạn ';
          (this.type = 'success'), (this.showModalResponse = true);
        },
        error: (error) => {
          console.log(error);
          this.message = error.error;
          this.type = 'error';
          this.showModalResponse = true;
        },
      })
    );
  }

  reportListOrderWithoutImportingForm() {
    this.subscriptions.push(
      this.reportService.reportListOrderWithouImportingForm().subscribe({
        next: (response) => {
          console.log(response);
          this.message =
            'Báo cáo đã được tạo và lưu vào folder D\\JasperReportHolder\\orderWithoutImportingFormReport. ' +
            'Hãy kiểm tra thư mục trên máy tính của bạn ';

          this.type = 'success';
          this.showModalResponse = true;
        },
        error: (error) => {
          console.log(error);
          this.message = error.error;
          this.type = 'error';
          this.showModalResponse = true;
        },
      })
    );
  }

  closeModalMessage() {
    this.showModalResponse = false;
  }
  //Employee Activity
  openModalEmployeeActivityRequest() {
    this.showModalEmployeeActivity = true;
  }

  closeModalEmployeeActivityRequest() {
    this.showModalEmployeeActivity = false;
  }

  showReportActivityResult(type: 'success' | 'error') {
    if (type === 'success') {
      this.message =
        'Báo cáo đã được tạo và lưu vào folder D\\JasperReportHolder\\EmployeeActivityReport.png ' +
        'Hãy kiểm tra thư mục trên máy tính của bạn ';
      this.type = 'success';
      this.showModalResponse = true;
    } else {
      this.message = 'Tạo Báo Cáo Thất Bại';
      this.type = 'error';
      this.showModalResponse = false;
    }
  }

  //Summary And Importing Report
  openModalSummaryImportAndExportReportForm() {
    this.showModalSummaryReport = true;
  }
  closeModalSummaryImportAndExportReportForm() {
    this.showModalSummaryReport = false;
  }

  showReportSummaryResult(type: 'success' | 'error') {
    if (type === 'success') {
      this.message =
        'Báo cáo đã được tạo và lưu vào folder D\\JasperReportHolder\\SummaryReport.png ' +
        'Hãy kiểm tra thư mục trên máy tính của bạn ';
      this.type = 'success';
      this.showModalResponse = true;
    } else {
      this.message = 'Tạo Báo Cáo Thất Bại';
      this.type = 'error';
      this.showModalResponse = true;
    }
  }

  //Detail Of Exporting or Importing Report
  openModalDetailExportingOrImportingReportForm() {
    this.showModalExportingOrImporting = true;
  }

  closeModalDetailExportingOrImportingReportForm() {
    this.showModalExportingOrImporting = false;
  }

  showReportDetailsResult(type: 'success' | 'error') {
    if (type === 'success') {
      this.message =
        'Báo cáo đã được tạo và lưu vào folder D\\JasperReportHolder\\DetailReport.png ' +
        'Hãy kiểm tra thư mục trên máy tính của bạn ';
      this.type = 'success';
      this.showModalResponse = true;
    } else {
      this.message = 'Tạo Báo Cáo Thất Bại';
      this.type = 'error';
      this.showModalResponse = true;
    }
  }
}
