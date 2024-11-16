import { Component, OnDestroy, OnInit } from '@angular/core';
import { ImportingFormService } from '../../../service/importing-form.service';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { ImportingFormDTOForDetailFunction } from '../../../model/importing-form/importing-form-detail-function.model';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-importing-form-detail',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './importing-form-detail.component.html',
  styleUrl: './importing-form-detail.component.css',
})
export class ImportingFormDetailComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  importingFormId: string = '';
  importingForm: ImportingFormDTOForDetailFunction | null = null;
  constructor(
    private importingFormService: ImportingFormService,
    private activateRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    let pathVariable = this.activateRoute.snapshot.paramMap.get('id');

    if (pathVariable) {
      this.importingFormId = pathVariable;
      this.subscriptions.push(
        this.importingFormService
          .getImportingFormForDetailFunction(this.importingFormId)
          .subscribe({
            next: (response) => {
              console.log(response);
              this.importingForm = response.body;
            },
            error: (error) => {
              console.log(error);
            },
          })
      );
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  onClose() {
    this.router.navigate(['/inventory/importing_form']);
  }
}
