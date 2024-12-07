import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ImportingFormService } from '../../../service/importing-form.service';
import {
  importingFormIdExistValidator,
  quantityValidator,
} from '../../../validators/importing_form.validator';
import { ActivatedRoute, Router } from '@angular/router';
import { Inventory } from '../../../model/inventories/inventories.model';
import { InventoriesManagementService } from '../../../service/inventories-management.service';
import { Subscription } from 'rxjs';
import {
  minFormArrayLength,
  supplierValidator,
} from '../../../validators/inventory-order.validator';
import { OrderDetailsListComponent } from '../../../sub-component/order-details-list/order-details-list.component';
import { OrderDTO } from '../../../model/order/order-dto.model';
import { OrderDetailDTO } from '../../../model/order/order-detail-dto.model';
import { ImportingFormDTO } from '../../../model/importing-form/importing-form-dto.model';
import { MessageModalComponent } from '../../../message-modal/message-modal.component';
import {
  ImportingFormValidationMessage,
  ValidationMessages,
} from '../../../../environments/validation_message_for_importingform';

@Component({
  selector: 'app-importing-form-create-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    OrderDetailsListComponent,
    MessageModalComponent,
  ],
  templateUrl: './importing-form-create-form.component.html',
  styleUrl: './importing-form-create-form.component.css',
})
export class ImportingFormCreateFormComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  form: any;
  orderIdSelected: string = '';
  //Inventory
  inventory: Inventory[] = [];
  showOrderDetailsList = false;
  //Modal Response
  showModalResponse = false;
  message: string = '';
  type: 'success' | 'error' = 'success';

  errorMessages: ValidationMessages = ImportingFormValidationMessage;

  constructor(
    private formBuilder: FormBuilder,
    private importingFormService: ImportingFormService,
    private activatedRoute: ActivatedRoute,
    private inventoryService: InventoriesManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.orderIdSelected = this.activatedRoute.snapshot.paramMap.get(
      'orderId'
    ) as string;

    this.subscriptions.push(
      this.inventoryService.listAll().subscribe((response) => {
        this.inventory = response.body as Inventory[];
      })
    );

    this.form = this.formBuilder.group({
      importingFormId: [
        '',
        {
          validators: [
            Validators.required,
            Validators.minLength(10),
            Validators.pattern(
              '^[a-zA-Z0-9-_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯăặẹẽềệỉịọỏộơớờờỡưứừửữỳỵỷỹ ]+$'
            ),
          ],
          asyncValidators: [
            importingFormIdExistValidator(this.importingFormService),
          ],
          updateOn: 'blur',
        },
      ],
      inventoryOrder: [{ value: '', disabled: true }, [Validators.required]],
      inventory: [
        '',
        {
          validators: [Validators.required, supplierValidator],
        },
      ],
      importingDetails: this.formBuilder.array([], minFormArrayLength(1)),
    });

    this.form.patchValue({
      inventoryOrder: this.orderIdSelected,
    });
  }

  get importingDetails() {
    return this.form.get('importingDetails') as FormArray;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  onSubmit() {
    if (this.form) {
      let requestBody: ImportingFormDTO = this.form.getRawValue();
      console.log(requestBody);
      this.subscriptions.push(
        this.importingFormService.createImportingForm(requestBody).subscribe({
          next: (response) => {
            console.log(response);
            this.message = 'Thành Công Tạo Phiếu Nhập';
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
  }

  openModalOrderDetailList() {
    this.showOrderDetailsList = true;
  }
  closeModalOrderDetailList() {
    this.showOrderDetailsList = false;
  }

  addImportingDetail(item: OrderDetailDTO) {
    const existingDetail = this.importingDetails.controls.find(
      (control) => control.get('productId')?.value === item.productId
    );
    if (existingDetail) {
      alert('Chi tiết đơn hàng này đã được chọn rồi.');
    } else {
      const detailGroup = this.formBuilder.group({
        productId: [item.productId, Validators.required],
        quantity: [
          '',
          [
            Validators.required,
            Validators.pattern('^[0-9]+$'),
            Validators.min(1),
            quantityValidator(item.quantity),
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
      });
      this.importingDetails.push(detailGroup);
      this.closeModalOrderDetailList();
    }
  }

  removeImportingDetail(index: number) {
    this.importingDetails.removeAt(index);
    this.importingDetails.markAsTouched(); // Mark as touched to show the error this.importingDetails.updateValueAndValidity()
    this.importingDetails.updateValueAndValidity();
  }

  closeModalResponse() {
    if (this.type === 'success') {
      this.importingFormService.setPageNum(1);
      this.router.navigate(['/inventory/importing_form']);
    }
    (this.message = ''), (this.type = 'success');
    this.showModalResponse = false;
  }

  getErrorMessage(
    controlName: string,
    errorName: string,
    index?: number
  ): string {
    if (index !== undefined) {
      //console.log('Get Message Error For Detail', index);
      const importingFormDetails = this.errorMessages[
        'importingDetails'
      ] as ValidationMessages;
      const controlErrors = importingFormDetails[
        controlName
      ] as ValidationMessages;
      return (controlErrors[errorName] as string) || '';
    }
    const controlErrors = this.errorMessages[controlName] as ValidationMessages;
    return (controlErrors[errorName] as string) || '';
  }
}
