import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Subscription } from 'rxjs';
import { MessageModalComponent } from '../../../message-modal/message-modal.component';
import { InventoryProviderService } from '../../../service/inventory-provider.service';
import { emailUniqueValidator } from '../../../validators/provider.validator';
import { InventoryProvider } from '../../../model/provider/inventory_provider.model';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-inventory-provider-create-form',
  standalone: true,
  imports: [ReactiveFormsModule, MessageModalComponent, RouterModule],
  templateUrl: './inventory-provider-create-form.component.html',
  styleUrl: './inventory-provider-create-form.component.css',
})
export class InventoryProviderCreateFormComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  form: any;

  //Modal Response
  showModal = false;
  message: string = '';
  type: 'success' | 'error' = 'success';

  constructor(
    private formBuilder: FormBuilder,
    private inventoryProviderService: InventoryProviderService,
    private router: Router
  ) {}

  createProvider() {
    if (this.form.valid) {
      let requestBody: InventoryProvider = this.form.value;
      console.log('Request Body', requestBody);
      this.subscriptions.push(
        this.inventoryProviderService.createProvider(requestBody).subscribe({
          next: (response) => {
            if (response.status == 200) {
              this.message = 'Create Provider Success';
              this.type = 'success';
              this.showModal = true;
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

  ngOnInit(): void {
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
            emailUniqueValidator(this.inventoryProviderService, null),
          ],
        },
      ],
      enabled: [false],
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
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
