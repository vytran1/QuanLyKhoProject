import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ExportingFormDetails } from '../../../model/exporting_form/exporting-form-detail-function.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ExportingFormService } from '../../../service/exporting-form.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-exporting-form-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './exporting-form-detail.component.html',
  styleUrl: './exporting-form-detail.component.css',
})
export class ExportingFormDetailComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  exportingFormDetail: ExportingFormDetails | null = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private exportingFormService: ExportingFormService,
    private router: Router
  ) {}

  ngOnInit(): void {
    let exportingFormId = this.activatedRoute.snapshot.paramMap.get('id');
    if (exportingFormId) {
      this.subscriptions.push(
        this.exportingFormService
          .getFullDetailOfOneExportingForm(exportingFormId)
          .subscribe({
            next: (response) => {
              if (response.status === 200) {
                console.log(response);
                this.exportingFormDetail = response.body;
              }
            },
            error: (error) => {
              console.error(error);
            },
          })
      );
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  onClose() {
    this.router.navigate(['/inventory/exporting_form']);
  }
}
