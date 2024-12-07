import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Subscription } from 'rxjs';
import { exportingFormIdValidator } from '../../../validators/exporting_form.validator';
import { ExportingFormService } from '../../../service/exporting-form.service';
import { Inventory } from '../../../model/inventories/inventories.model';
import { InventoriesManagementService } from '../../../service/inventories-management.service';
import {
  minFormArrayLength,
  supplierValidator,
} from '../../../validators/inventory-order.validator';
import { ListStockByInventoryComponent } from '../../../sub-component/list-stock-by-inventory/list-stock-by-inventory.component';
import { ProductQuantity } from '../../../model/product_inventory/product-quantity.model';
import { ExportingFormDTO } from '../../../model/exporting_form/exporting-form-dto.model';
import { MessageModalComponent } from '../../../message-modal/message-modal.component';
import { Router } from '@angular/router';
import {
  ExportingFormValidationMessage,
  ValidationMessages,
} from '../../../../environments/validation_message_for_exportingform';

@Component({
  selector: 'app-exporting-form-create-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ListStockByInventoryComponent,
    MessageModalComponent,
  ],
  templateUrl: './exporting-form-create-form.component.html',
  styleUrl: './exporting-form-create-form.component.css',
})
export class ExportingFormCreateFormComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  form: any;
  inventories: Inventory[] = [];
  //Show modal Stock In Inventory
  showModalStock = false;
  //Show modal response
  showModalResponse = false;
  message: string = '';
  type: 'success' | 'error' = 'success';

  errorMessages: ValidationMessages = ExportingFormValidationMessage;

  constructor(
    private formBuilder: FormBuilder,
    private exportingFormService: ExportingFormService,
    private inventoryService: InventoriesManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      exportingFormId: [
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
            exportingFormIdValidator(this.exportingFormService),
          ],
          updateOn: 'blur',
        },
      ],
      inventory: [
        '',
        {
          validators: [Validators.required, supplierValidator],
        },
      ],
      customerName: [
        '',
        {
          validators: [
            Validators.required,
            Validators.pattern(/^[a-zA-ZÀ-ỹ\s]+$/u),
            Validators.minLength(10),
          ],
        },
      ],
      customerPhoneNumber: [
        '',
        {
          validators: [Validators.required, Validators.pattern('^[0-9]{10}$')],
        },
      ],
      exportingDetails: this.formBuilder.array([], minFormArrayLength(1)),
    });

    this.subscriptions.push(
      this.inventoryService.listAll().subscribe({
        next: (response) => {
          this.inventories = response.body as Inventory[];
        },
        error: (error) => {
          console.error(error);
        },
      })
    );

    this.subscriptions.push(
      this.form.get('inventory')?.valueChanges.subscribe(() => {
        this.resetExportingDetails();
      })
    );
  }

  get exportingDetails() {
    return this.form.get('exportingDetails') as FormArray;
  }

  resetExportingDetails() {
    this.exportingDetails.clear();
  }

  removeImportingDetail(i: number) {
    this.exportingDetails.removeAt(i);
    this.exportingDetails.markAsTouched(); // Mark as touched to show the error this.importingDetails.updateValueAndValidity()
    this.exportingDetails.updateValueAndValidity();
  }
  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  onSubmit() {
    // console.log('Value Form:');
    // console.log(this.form.value);
    let requestBody = this.form.value as ExportingFormDTO;
    console.log('Request Body');
    console.log(requestBody);
    this.subscriptions.push(
      this.exportingFormService.createExportingForm(requestBody).subscribe({
        next: (response) => {
          if (response.status === 200) {
            this.message = 'Thành công tạo phiếu xuất';
            this.type = 'success';
            this.showModalResponse = true;
          }
        },
        error: (error) => {
          console.log(error);
          (this.message = error.error),
            (this.type = 'error'),
            (this.showModalResponse = true);
        },
      })
    );
  }

  openModalListStock() {
    this.showModalStock = true;
  }
  closeModalListStock() {
    this.showModalStock = false;
  }

  addExportingDetail(item: ProductQuantity) {
    const existingDetail = this.exportingDetails.controls.find(
      (control) => control.get('productId')?.value === item.productId
    );

    if (existingDetail) {
      alert('Sản phẩm này đã được chọn trong chi tiết phiếu xuất.');
    } else {
      const detailGroup = this.formBuilder.group({
        productId: [item.productId, Validators.required],
        quantity: [
          '',
          [
            Validators.required,
            Validators.pattern('^[0-9]+$'),
            Validators.min(1),
            Validators.max(item.quantity), // Đảm bảo không xuất quá số lượng tồn kho
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

      this.exportingDetails.push(detailGroup);
      this.closeModalListStock();
    }
  }

  closeModalResponse() {
    if (this.type === 'success') {
      this.exportingFormService.setPageNum(1);
      this.router.navigate(['/inventory/exporting_form']);
    }

    this.message = '';
    this.type = 'success';
    this.showModalResponse = false;
  }

  getErrorMessage(
    controlName: string,
    errorName: string,
    index?: number
  ): string {
    if (index !== undefined) {
      //console.log('Get Message Error For Detail', index);
      const exportingFormDetails = this.errorMessages[
        'exportingDetails'
      ] as ValidationMessages;
      const controlErrors = exportingFormDetails[
        controlName
      ] as ValidationMessages;
      return (controlErrors[errorName] as string) || '';
    }
    const controlErrors = this.errorMessages[controlName] as ValidationMessages;
    return (controlErrors[errorName] as string) || '';
  }
}
