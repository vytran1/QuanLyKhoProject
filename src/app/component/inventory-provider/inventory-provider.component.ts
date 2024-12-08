import { Component, OnDestroy, OnInit } from '@angular/core';
import { InventoryProvider } from '../../model/provider/inventory_provider.model';
import { Subscription } from 'rxjs';
import { InventoryProviderService } from '../../service/inventory-provider.service';
import { SearchComponent } from '../../search/search.component';
import { ImprovedPaginationComponent } from '../../improved-pagination/improved-pagination.component';
import { ChangePageSizeComponent } from '../../change-page-size/change-page-size.component';
import { CommonModule } from '@angular/common';
import { InventoryProviderDetailModalComponent } from './inventory-provider-detail-modal/inventory-provider-detail-modal.component';
import { RouterModule } from '@angular/router';
import { DeleteInventoryUserWarningModalComponent } from '../../inventory-user-management/delete-inventory-user-warning-modal/delete-inventory-user-warning-modal.component';
import { MessageModalComponent } from '../../message-modal/message-modal.component';

@Component({
  selector: 'app-inventory-provider',
  standalone: true,
  imports: [
    SearchComponent,
    ImprovedPaginationComponent,
    ChangePageSizeComponent,
    CommonModule,
    InventoryProviderDetailModalComponent,
    RouterModule,
    DeleteInventoryUserWarningModalComponent,
    MessageModalComponent,
  ],
  templateUrl: './inventory-provider.component.html',
  styleUrl: './inventory-provider.component.css',
})
export class InventoryProviderComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  inventoryProviders: InventoryProvider[] = [];
  pageNum = 1;
  totalPage = 0;
  totalItems = 0;
  sortField = 'providerId';
  sortDir = 'asc';

  //Modal Details
  showModalDetails: boolean = false;
  selectedInventoryProvider: InventoryProvider | null = null;

  //Modal Warning
  showModalDelete: boolean = false;
  deleteMessage = 'Are your sure to delete this provider';
  selectedProvider: InventoryProvider | null = null;

  //Modal Response
  showModalMessage: boolean = false;
  message: string = '';
  type: 'success' | 'error' = 'success';

  constructor(private inventoryProviderService: InventoryProviderService) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.inventoryProviderService.inventoryProviders$.subscribe(
        (response) => {
          this.inventoryProviders = response;
        }
      )
    );

    this.subscriptions.push(
      this.inventoryProviderService.pageNum$.subscribe((response) => {
        this.pageNum = response;
      })
    );

    this.subscriptions.push(
      this.inventoryProviderService.totalPage$.subscribe((response) => {
        this.totalPage = response;
      })
    );

    this.subscriptions.push(
      this.inventoryProviderService.totalItems$.subscribe((response) => {
        this.totalItems = response;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  onKeyWord(value: string) {
    this.inventoryProviderService.setKeyWord(value);
  }

  onSortFieldChange(sortField: string) {
    if (this.sortField === sortField) {
      this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = sortField;
      this.sortDir = 'asc';
    }
    this.inventoryProviderService.setSortFieldAndSortDir(
      this.sortField,
      this.sortDir
    );
  }

  onPageNumChange(value: number) {
    this.inventoryProviderService.setPageNum(value);
  }

  onPageSizeChange(value: number) {
    this.inventoryProviderService.setPageSize(value);
  }

  openModalDetails(provider: InventoryProvider) {
    this.selectedInventoryProvider = provider;
    this.showModalDetails = true;
  }

  closeModalDetails() {
    this.showModalDetails = false;
  }

  openModalDelete(provider: InventoryProvider) {
    this.selectedInventoryProvider = provider;
    this.showModalDelete = true;
  }

  confirmDeleteOperation() {
    console.log(
      `Confirm Delete Provider with ID: ${this.selectedInventoryProvider?.providerId}`
    );
    if (this.selectedInventoryProvider) {
      this.subscriptions.push(
        this.inventoryProviderService
          .deleteProviderById(this.selectedInventoryProvider.providerId)
          .subscribe({
            next: (response) => {
              this.message = 'Update Provider Successful';
              this.type = 'success';
              this.showModalMessage = true;
              this.inventoryProviderService.setPageNum(1);
            },
            error: (error) => {
              this.message = error.error;
              this.type = 'error';
              this.showModalMessage = true;
            },
          })
      );
    }
    this.selectedInventoryProvider = null;
    this.showModalDelete = false;
  }

  cancelDeleteOperation() {
    this.selectedInventoryProvider = null;
    this.showModalDelete = false;
  }

  closeModalMessage() {
    this.message = '';
    this.type = 'success';
    this.showModalMessage = false;
  }
}
