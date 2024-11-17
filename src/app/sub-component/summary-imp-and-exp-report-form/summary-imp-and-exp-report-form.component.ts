import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { dateRangeValidator } from '../../validators/inventory.validator';
import { Subscription } from 'rxjs';
import { end } from '@popperjs/core';
import { SummaryExportAndImportRequest } from '../../model/report/summary-imp-and-exp-request.model';
import { EventManager } from '@angular/platform-browser';
import { InventoryReportService } from '../../service/inventory-report.service';

@Component({
  selector: 'app-summary-imp-and-exp-report-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './summary-imp-and-exp-report-form.component.html',
  styleUrl: './summary-imp-and-exp-report-form.component.css',
})
export class SummaryImpAndExpReportFormComponent implements OnInit, OnDestroy {
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

  onSubmit() {
    console.log(this.form.value);
    if (this.form.valid) {
      let formValues = this.form.value;

      let startDate = this.formatDateTimeLocal(formValues.startDate);
      let endDate = this.formatDateTimeLocal(formValues.endDate);

      const requestBody: SummaryExportAndImportRequest = {
        startDate: startDate,
        endDate: endDate,
      };

      this.subscriptions.push(
        this.reportService.reportSummaryImpAndExp(requestBody).subscribe({
          next: (response) => {
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

  closeForm() {
    this.closeEmit.emit();
  }
}
