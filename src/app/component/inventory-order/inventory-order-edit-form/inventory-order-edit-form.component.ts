import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { InventoryOrderService } from '../../../service/inventory-order.service';
import {
  FormArray,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { OrderDTO } from '../../../model/order/order-dto.model';
import {
  minFormArrayLength,
  orderIdExistValidator,
} from '../../../validators/inventory-order.validator';
import { ProductListModalComponent } from '../../../sub-component/product-list-modal/product-list-modal.component';
import { MessageModalComponent } from '../../../message-modal/message-modal.component';
import { Inventory } from '../../../model/inventories/inventories.model';
import { InventoriesManagementService } from '../../../service/inventories-management.service';
import { InventoryProvider } from '../../../model/provider/inventory_provider.model';
import { InventoryProviderService } from '../../../service/inventory-provider.service';
import {
  ValidationMessages,
  OrderFormValidationMessage,
} from '../../../../environments/validation_message_for_orderform';

@Component({
  selector: 'app-inventory-order-edit-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ProductListModalComponent,
    MessageModalComponent,
  ],
  templateUrl: './inventory-order-edit-form.component.html',
  styleUrl: './inventory-order-edit-form.component.css',
})
export class InventoryOrderEditFormComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  form: any;
  inventoryOrder: OrderDTO | null = null;
  inventoryProviders: InventoryProvider[] = [];
  showProductList = false;
  inventories: Inventory[] = [];
  selectedInventory: Inventory | null = null;
  errorMessages: ValidationMessages = OrderFormValidationMessage;
  //Modal Response
  message = '';
  type: 'success' | 'error' = 'success';
  showResponseModal = false;

  constructor(
    private activeRouter: ActivatedRoute,
    private inventoryOrderService: InventoryOrderService,
    private formBuilder: FormBuilder,
    private router: Router,
    private inventoryService: InventoriesManagementService,
    private inventoryProviderService: InventoryProviderService
  ) {}

  ngOnInit(): void {
    let orderId = this.activeRouter.snapshot.paramMap.get('id') as string;

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

    this.form = this.formBuilder.group({
      orderId: [{ value: '', disabled: true }, [Validators.required]],
      providerId: [{ value: '', disabled: true }, [Validators.required]],
      customerName: [
        { value: '', disabled: true },
        [Validators.required, Validators.minLength(10)],
      ],
      customerPhoneNumber: [
        { value: ' ', disabled: true },
        [Validators.required, Validators.pattern('^[0-9]{10}$')],
      ],
      inventoryId: [{ value: ' ', disabled: true }, [Validators.required]],
      orderDetails: this.formBuilder.array([], minFormArrayLength(1)),
    });

    this.subscriptions.push(
      this.inventoryOrderService
        .getOrderInfoForUpdateFunction(orderId)
        .subscribe({
          next: (response) => {
            if (response.body) {
              console.log(response.body);
              this.inventoryOrder = response.body;
              if (this.inventoryOrder) {
                this.form.patchValue({
                  orderId: this.inventoryOrder.orderId,
                  providerId: this.inventoryOrder.providerId,
                  customerName: this.inventoryOrder.customerName,
                  customerPhoneNumber: this.inventoryOrder.customerPhoneNumber,
                  inventoryId: this.inventoryOrder.inventoryId,
                });
                this.inventoryOrder?.orderDetails.forEach((orderDetail) => {
                  this.orderDetails.push(
                    this.formBuilder.group({
                      productId: [orderDetail.productId],
                      quantity: [
                        orderDetail.quantity,
                        [
                          Validators.required,
                          Validators.pattern('^[0-9]+$'),
                          Validators.min(1),
                        ],
                      ],
                      unitPrice: [
                        orderDetail.unitPrice,
                        [
                          Validators.required,
                          Validators.pattern('^[0-9]+$'),
                          Validators.min(1),
                        ],
                      ],
                    })
                  );
                });
              }
            }
          },
          error: (error) => {
            console.log(error);
          },
        })
    );
  }

  get orderDetails() {
    return this.form.get('orderDetails') as FormArray;
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
    if (this.form.valid) {
      let requestBody = this.form.getRawValue();
      this.subscriptions.push(
        this.inventoryOrderService.updateOrder(requestBody).subscribe({
          next: (response) => {
            if (response.status === 200) {
              (this.message = 'Cập nhật đơn hàng thành công'),
                (this.type = 'success');
              this.showResponseModal = true;
            }
          },
          error: (error) => {
            console.log(error);

            this.message = error.error;
            this.type = 'error';
            this.showResponseModal = true;
          },
        })
      );
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
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
      this.inventoryOrderService.setPageNum(1);
      this.router.navigate(['/inventory/inventory_order']);
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
