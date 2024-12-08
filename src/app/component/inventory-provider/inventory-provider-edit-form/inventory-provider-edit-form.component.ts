import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { InventoryProviderService } from '../../../service/inventory-provider.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { emailUniqueValidator } from '../../../validators/provider.validator';
import { MessageModalComponent } from '../../../message-modal/message-modal.component';
import { InventoryProvider } from '../../../model/provider/inventory_provider.model';

@Component({
  selector: 'app-inventory-provider-edit-form',
  standalone: true,
  imports: [MessageModalComponent, ReactiveFormsModule, RouterModule],
  templateUrl: './inventory-provider-edit-form.component.html',
  styleUrl: './inventory-provider-edit-form.component.css',
})
export class InventoryProviderEditFormComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  providerId!: number;
  form: any;

  //ShowModalResponse
  message: string = '';
  type: 'success' | 'error' = 'success';
  showModal = false;

  constructor(
    private inventoryProviderService: InventoryProviderService,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.providerId = Number(
      this.activatedRoute.snapshot.paramMap.get('providerId')!
    );

    this.form = this.formBuilder.group({
      providerName: [
        '',
        {
          validators: [
            Validators.required,
            Validators.minLength(10),
            Validators.pattern('^[A-Za-z0-9À-ỹà-ỹ\\s\\-_,.]+$'),
          ],
        },
      ],
      providerContactNumber: [
        '',
        {
          validators: [Validators.required, Validators.pattern('^\\d{10,11}$')],
        },
      ],
      providerAddress: [
        '',
        {
          validators: [Validators.required, Validators.minLength(10)],
        },
      ],
      providerEmail: [
        '',
        {
          validators: [Validators.required, Validators.email],
          asyncValidators: [
            emailUniqueValidator(
              this.inventoryProviderService,
              this.providerId
            ),
          ],
        },
      ],
      enabled: [false],
    });

    this.subscriptions.push(
      this.inventoryProviderService.getProviderById(this.providerId).subscribe({
        next: (response) => {
          this.form.patchValue(response.body);
        },
        error: (error) => {
          this.router.navigate(['/inventory/inventory_provider']);
        },
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  editProvider() {
    if (this.form.valid) {
      let requestBody: InventoryProvider = this.form.value;
      console.log('Request Body', requestBody);
      this.subscriptions.push(
        this.inventoryProviderService
          .editProvider(this.providerId, requestBody)
          .subscribe({
            next: (response) => {
              if (response.status === 200) {
                this.message = 'Update Provider Successful';
                (this.type = 'success'), (this.showModal = true);
              }
            },
            error: (error) => {
              console.log(error);
              this.message = error.error;
              this.type = 'error';
              this.showModal = true;
            },
          })
      );
    }
  }

  closeModalMessage() {
    if (this.type == 'success') {
      this.message = '';
      this.type = 'success';
      this.showModal = false;
      this.router.navigate(['/inventory/inventory_provider']);
      this.inventoryProviderService.setPageNum(1);
    } else {
      this.message = '';
      this.type = 'success';
      this.showModal = false;
    }
  }
}
