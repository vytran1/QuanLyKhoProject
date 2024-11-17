import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { InventoryUserDTOForReport } from '../../model/inventory-user/inventory-user-dto-for-report.model';
import { InventoryUserManagementService } from '../../service/inventory-user-management.service';
import { InventoryReportService } from '../../service/inventory-report.service';
import { EmployeeActivityReport } from '../../model/report/employee-activity-report-request.model';
import { DatePipe } from '@angular/common';
import { dateRangeValidator } from '../../validators/inventory.validator';
import { supplierValidator } from '../../validators/inventory-order.validator';

@Component({
  selector: 'app-employee-activity-report-request-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './employee-activity-report-request-form.component.html',
  styleUrl: './employee-activity-report-request-form.component.css',
  providers: [DatePipe],
})
export class EmployeeActivityReportRequestFormComponent
  implements OnInit, OnDestroy
{
  subscriptions: Subscription[] = [];
  form: any;
  inventoriesUsers: InventoryUserDTOForReport[] = [];

  @Output()
  closeEmit = new EventEmitter();

  @Output()
  reportResultEmit = new EventEmitter<'success' | 'error'>();

  constructor(
    private formBuilder: FormBuilder,
    private inventoryUserService: InventoryUserManagementService,
    private reportService: InventoryReportService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        userId: ['', [Validators.required, supplierValidator]],
        startDate: [null, [Validators.required]],
        endDate: [null, [Validators.required]],
      },
      {
        validators: [dateRangeValidator],
      }
    );

    this.subscriptions.push(
      this.inventoryUserService.getListUserForActivityReport().subscribe({
        next: (response) => {
          console.log(response);
          this.inventoriesUsers = response.body;
        },
        error: (error) => {
          console.log(error);
        },
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  onSubmit() {
    console.log(this.form.value);
    if (this.form.valid) {
      const formValues = this.form.value;
      const startDate =
        this.datePipe.transform(formValues.startDate, 'yyyy-MM-dd HH:mm:ss') ||
        '';
      const endDate =
        this.datePipe.transform(formValues.endDate, 'yyyy-MM-dd HH:mm:ss') ||
        '';
      let requestBody = {
        userId: formValues.userId,
        startDate: startDate,
        endDate: endDate,
      };
      this.subscriptions.push(
        this.reportService.reportEmployeeActivity(requestBody).subscribe({
          next: (response) => {
            console.log(response);
            this.reportResultEmit.emit('success');
          },
          error: (error) => {
            console.log(error);
            this.reportResultEmit.emit('error');
          },
        })
      );
    }
  }

  closeForm() {
    this.closeEmit.emit();
  }
}
