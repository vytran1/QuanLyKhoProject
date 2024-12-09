import { Component, OnDestroy, OnInit } from '@angular/core';
import { Inventory } from '../../../model/inventories/inventories.model';
import {
  FormArray,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { InventoryOrderService } from '../../../service/inventory-order.service';
import {
  minFormArrayLength,
  orderIdExistValidator,
  supplierValidator,
} from '../../../validators/inventory-order.validator';
import { Subscription } from 'rxjs';
import { InventoriesManagementService } from '../../../service/inventories-management.service';
import { ProductListModalComponent } from '../../../sub-component/product-list-modal/product-list-modal.component';
import { OrderDTO } from '../../../model/order/order-dto.model';
import { Router } from '@angular/router';
import { MessageModalComponent } from '../../../message-modal/message-modal.component';
import {
  OrderFormValidationMessage,
  ValidationMessages,
} from '../../../../environments/validation_message_for_orderform';
import { InventoryProviderService } from '../../../service/inventory-provider.service';
import { InventoryProvider } from '../../../model/provider/inventory_provider.model';

@Component({
  selector: 'app-inventory-order-create-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ProductListModalComponent,
    MessageModalComponent,
  ],
  templateUrl: './inventory-order-create-form.component.html',
  styleUrl: './inventory-order-create-form.component.css',
})
export class InventoryOrderCreateFormComponent implements OnInit, OnDestroy {
  form: any;
  inventories: Inventory[] = [];
  inventoryProviders: InventoryProvider[] = [];
  selectedInventory: Inventory | null = null;
  subscriptions: Subscription[] = [];
  showProductList = false;
  //Modal Response
  message = '';
  type: 'success' | 'error' = 'success';
  showResponseModal = false;

  errorMessages: ValidationMessages = OrderFormValidationMessage;

  constructor(
    private formBuilder: FormBuilder,
    private inventoryOrderService: InventoryOrderService,
    private inventoryService: InventoriesManagementService,
    private inventoryProviderService: InventoryProviderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      orderId: [
        '',
        {
          validators: [
            Validators.required,
            Validators.minLength(10),
            Validators.pattern(
              '^[a-zA-Z0-9-_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯăặẹẽềệỉịọỏộơớờờỡưứừửữỳỵỷỹ ]+$'
            ),
          ],
          asyncValidators: [orderIdExistValidator(this.inventoryOrderService)],
          updateOn: 'blur',
        },
      ],
      //supplier: ['', [Validators.required, supplierValidator]],
      customerName: [
        '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.pattern(/^[a-zA-ZÀ-ỹ\s]+$/u),
        ],
      ],
      customerPhoneNumber: [
        '',
        [Validators.required, Validators.pattern('^[0-9]{10}$')],
      ],
      inventoryId: ['', [Validators.required, supplierValidator]],
      providerId: ['', [Validators.required, supplierValidator]],
      orderDetails: this.formBuilder.array([], minFormArrayLength(1)),
    });

    this.subscriptions.push(
      this.inventoryService.listAll().subscribe({
        next: (response) => {
          if (response.body) {
            this.inventories = response.body as Inventory[];
          }
        },
      })
    );

    this.subscriptions.push(
      this.inventoryProviderService.findAllWithOnlyIdAndName().subscribe({
        next: (response) => {
          if (response.body) {
            this.inventoryProviders = response.body as InventoryProvider[];
          }
        },
      })
    );

    const inventoryChangeSubscription = this.form
      .get('inventoryId')
      .valueChanges.subscribe((inventoryId: string) => {
        this.selectedInventory =
          this.inventories.find(
            (inventory) => inventory.inventoryId === inventoryId
          ) || null;
      });
    this.subscriptions.push(inventoryChangeSubscription);
  }

  get orderDetails() {
    return this.form.get('orderDetails') as FormArray;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  openProductList() {
    this.showProductList = true;
  }

  addProductToOrder(productId: number): void {
    const productExist = this.orderDetails.controls.some(
      (detail) => detail.get('productId')?.value === productId
    );
    if (!productExist) {
      this.orderDetails.push(
        this.formBuilder.group({
          productId: [productId],
          quantity: [
            '',
            [
              Validators.required,
              Validators.pattern('^[0-9]+$'),
              Validators.min(1),
            ],
          ],
          unitPrice: [
            '',
            [
              Validators.required,
              Validators.pattern('^[0-9]+$'),
              Validators.min(1),
            ],
          ],
        })
      );
    } else {
      alert('Sản phẩm này đã có trong đơn hàng');
      this.closeModalProductList();
    }
  }

  renderProduct(event: number) {
    console.log(event);
    this.addProductToOrder(event);
    this.showProductList = false;
  }
  closeModalProductList() {
    this.showProductList = false;
  }
  removeDetail(i: number) {
    this.orderDetails.removeAt(i);
    this.orderDetails.markAsTouched(); // Mark as touched to show the error
    this.orderDetails.updateValueAndValidity();
  }

  submitForm() {
    console.log(this.form.value);
    if (this.form.valid) {
      let requestBody: OrderDTO = this.form.value;
      this.subscriptions.push(
        this.inventoryOrderService.createOrder(requestBody).subscribe({
          next: (response) => {
            if (response.status === 200) {
              this.message = 'Thành công lập đơn hàng';
              this.type = 'success';
              this.showResponseModal = true;
            }
          },
          error: (error) => {
            this.message = error.error;
            this.type = 'error';
            this.showResponseModal = true;
          },
        })
      );
      console.log(requestBody);
    }
  }

  closeResponseModal() {
    if (this.type === 'success') {
      this.message = '';
      this.type = 'success';
      this.showResponseModal = false;
      this.inventoryOrderService.setPageNum(1);
      this.router.navigate(['/inventory/inventory_order']);
    } else {
      this.message = '';
      this.type = 'success';
      this.showResponseModal = false;
      this.form.reset();
    }
  }

  getErrorMessage(
    controlName: string,
    errorName: string,
    index?: number
  ): string {
    if (index !== undefined) {
      //console.log('Get Message Error For Detail', index);
      const orderDetails = this.errorMessages[
        'orderDetails'
      ] as ValidationMessages;
      const controlErrors = orderDetails[controlName] as ValidationMessages;
      return (controlErrors[errorName] as string) || '';
    }
    const controlErrors = this.errorMessages[controlName] as ValidationMessages;
    return (controlErrors[errorName] as string) || '';
  }
}
