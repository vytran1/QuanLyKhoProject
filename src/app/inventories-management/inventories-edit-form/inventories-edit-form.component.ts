import { Component, OnDestroy, OnInit } from '@angular/core';
import { MessageModalComponent } from '../../message-modal/message-modal.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { InventoriesManagementService } from '../../service/inventories-management.service';
import { Inventory } from '../../model/inventories/inventories.model';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inventories-edit-form',
  standalone: true,
  imports: [
    MessageModalComponent,
    ReactiveFormsModule,
    RouterModule,
    CommonModule,
  ],
  templateUrl: './inventories-edit-form.component.html',
  styleUrl: './inventories-edit-form.component.css',
})
export class InventoriesEditFormComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  //Form
  form: any;
  inventory: Inventory | null = null;
  //Modal
  showModal = false;
  message = '';
  type: 'success' | 'error' = 'success';

  constructor(
    private formBuilder: FormBuilder,
    private activeRouter: ActivatedRoute,
    private inventoryService: InventoriesManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    let inventoryId = this.activeRouter.snapshot.paramMap.get('id');

    this.form = this.formBuilder.group({
      inventoryId: ['', Validators.required],
      inventoryName: ['', [Validators.required, Validators.minLength(9)]],
      inventoryAddress: ['', [Validators.required, Validators.minLength(9)]],
      countryId: [{ value: '', disabled: true }, Validators.required],
      stateId: [{ value: '', disabled: true }, Validators.required],
      districtId: [{ value: '', disabled: true }, Validators.required],
    });

    this.loadData(inventoryId);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  loadData(inventoryId: any) {
    this.subscriptions.push(
      this.inventoryService.getInventoryById(inventoryId).subscribe({
        next: (response) => {
          this.inventory = response.body as Inventory;
          this.form.patchValue({
            inventoryId: this.inventory.inventoryId,
            inventoryName: this.inventory.inventoryName,
            inventoryAddress: this.inventory.inventoryAddress,
            countryId: this.inventory.countryId,
            stateId: this.inventory.stateId,
            districtId: this.inventory.districtId,
          });
        },
        error: (error) => {
          this.message = error.error;
          this.type = 'error';
          this.showModal = true;
        },
      })
    );
  }

  updateInventory() {
    console.log(this.form.value);
    let requestBody = this.form.value as Inventory;
    this.subscriptions.push(
      this.inventoryService.updateInventory(requestBody).subscribe({
        next: (response) => {
          this.message = 'Successfully update inventory information';
          this.type = 'success';
          this.showModal = true;
        },
        error: (error) => {
          this.message = error.error;
          this.type = 'error';
          this.showModal = true;
        },
      })
    );
  }
  closeModalMessage() {
    if (this.type === 'success') {
      this.message = '';
      this.type = 'success';
      this.showModal = false;
      this.inventoryService.setPageNum(1);
      this.router.navigate(['/inventory/inventories']);
    } else {
      this.message = '';
      this.type = 'success';
      this.showModal = false;
    }
  }
}
