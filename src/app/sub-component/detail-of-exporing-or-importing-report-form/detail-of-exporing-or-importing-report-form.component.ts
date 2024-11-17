import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { supplierValidator } from '../../validators/inventory-order.validator';
import { dateRangeValidator } from '../../validators/inventory.validator';
import { DetailOfImportingOrExportingRequest } from '../../model/report/detail-of-exporting-or-importing-request.model';
import { InventoryReportService } from '../../service/inventory-report.service';

@Component({
  selector: 'app-detail-of-exporing-or-importing-report-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './detail-of-exporing-or-importing-report-form.component.html',
  styleUrl: './detail-of-exporing-or-importing-report-form.component.css',
})
export class DetailOfExporingOrImportingReportFormComponent
  implements OnInit, OnDestroy
{
  subscriptions: Subscription[] = [];
  form: any;

  @Output()
  closeEmit = new EventEmitter();

  @Output()
  reportResultEmit = new EventEmitter<'success' | 'error'>();

  constructor(
    private formBuilder: FormBuilder,
    private reportService: InventoryReportService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        type: ['', [Validators.required, supplierValidator]],
        startDate: [null, [Validators.required]],
        endDate: [null, [Validators.required]],
      },
      {
        validators: [dateRangeValidator],
      }
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  closeForm() {
    this.closeEmit.emit();
  }

  formatDateTimeLocal(dateTimeLocal: string) {
    const date = new Date(dateTimeLocal);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  }

  onSubmit() {
    if (this.form.valid) {
      let formValues = this.form.value;

      const startDate = this.formatDateTimeLocal(formValues.startDate);
      const endDate = this.formatDateTimeLocal(formValues.endDate);

      let requestBody: DetailOfImportingOrExportingRequest = {
        type: formValues.type,
        startDate: startDate,
        endDate: endDate,
      };

      this.subscriptions.push(
        this.reportService
          .reportDetailOfImportingOrExporting(requestBody)
          .subscribe({
            next: (response) => {
              console.log(response);
              if (response.status === 200) {
                this.reportResultEmit.emit('success');
              } else {
                this.reportResultEmit.emit('error');
              }
            },
            error: (error) => {
              console.error(error);
              this.reportResultEmit.emit('error');
            },
          })
      );
    }
  }
}
