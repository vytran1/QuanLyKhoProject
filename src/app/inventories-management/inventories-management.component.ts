import { Component, OnDestroy, OnInit } from '@angular/core';
import { Inventory } from '../model/inventories/inventories.model';
import { InventoriesManagementService } from '../service/inventories-management.service';
import { Subscription } from 'rxjs';
import { ImprovedPaginationComponent } from '../improved-pagination/improved-pagination.component';
import { CommonModule } from '@angular/common';
import { SearchComponent } from '../search/search.component';
import { ChangePageSizeComponent } from '../change-page-size/change-page-size.component';
import { InventoriesModalDetailsComponent } from './inventories-modal-details/inventories-modal-details.component';
import { DeleteInventoryUserWarningModalComponent } from '../inventory-user-management/delete-inventory-user-warning-modal/delete-inventory-user-warning-modal.component';
import { MessageModalComponent } from '../message-modal/message-modal.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-inventories-management',
  standalone: true,
  imports: [
    ImprovedPaginationComponent,
    CommonModule,
    SearchComponent,
    ChangePageSizeComponent,
    InventoriesModalDetailsComponent,
    DeleteInventoryUserWarningModalComponent,
    MessageModalComponent,
    RouterModule,
  ],
  templateUrl: './inventories-management.component.html',
  styleUrl: './inventories-management.component.css',
})
export class InventoriesManagementComponent implements OnInit, OnDestroy {
  inventories: Inventory[] = [];
  subscriptions: Subscription[] = [];
  //Pagination
  pageNum = 1;
  pageSize = 2;
  sortField = 'inventoryName';
  sortDir = 'asc';
  totalPage = 0;
  totalItems = 0;

  //Detail
  selectedInventory: Inventory | null = null;
  showModal = false;

  //Delete Warning Modal
  showModalWarning = false;
  deleteInventory: Inventory | null = null;
  messageDelete = 'Are you sure to delete this modal';

  //Response Message Modal
  showModalMessage = false;
  message = '';
  type: 'success' | 'error' = 'success';

  constructor(private inventoryMnService: InventoriesManagementService) {}

  ngOnInit(): void {
    //this.loadData();

    this.subscriptions.push(
      this.inventoryMnService.inventories$.subscribe((response) => {
        this.inventories = response;
      })
    );

    this.subscriptions.push(
      this.inventoryMnService.pageNum$.subscribe((response) => {
        this.pageNum = response;
      })
    );
    this.subscriptions.push(
      this.inventoryMnService.totalPage$.subscribe((response) => {
        this.totalPage = response;
      })
    );
    this.subscriptions.push(
      this.inventoryMnService.totalItems$.subscribe((response) => {
        this.totalItems = response;
      })
    );
  }

  loadData() {
    this.subscriptions.push(
      this.inventoryMnService
        .getInventoriesByPage(1, 2, 'inventoryName', 'asc')
        .subscribe({
          next: (response) => {
            console.log('Inventories Load');
            console.log(response);
            if (response.body) {
              this.inventories = response.body.inventoryDTOs as Inventory[];
              this.totalItems = response.body.totalItems;
              this.totalPage = response.body.totalPage;
              this.pageNum = response.body.pageNum;
            }
          },
          error: (error) => {
            console.log('Error');
            console.log(error);
            if (error) {
            }
          },
        })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
  onPageChange(pageNum: any) {
    this.inventoryMnService.setPageNum(pageNum);
  }
  changeSort(sortField: string) {
    if (this.sortField === sortField) {
      this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = sortField;
      this.sortDir = 'asc';
    }
    this.inventoryMnService.setSortFieldAndSortDir(
      this.sortField,
      this.sortDir
    );
  }

  onSearch(event: any) {
    this.inventoryMnService.setKeyWord(event);
  }

  onPageSizeChange(event: any) {
    this.inventoryMnService.setPageSize(event);
  }
  showModalDetails(selectedInventory: Inventory) {
    this.selectedInventory = selectedInventory;
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  openModalWarningDelete(item: Inventory) {
    this.deleteInventory = item;
    this.showModalWarning = true;
  }

  confirmDeleteOperation() {
    let inventoryId = this.deleteInventory?.inventoryId;
    this.subscriptions.push(
      this.inventoryMnService.delete(inventoryId).subscribe({
        next: (response) => {
          console.log(response);
          this.cancelDeleteOperation();
          this.showModalMessage = true;
          this.message = 'Success Delete Inventory';
          this.type = 'success';
          this.loadData();
        },
        error: (error) => {
          console.log(error);
          this.cancelDeleteOperation();
          this.showModalMessage = true;
          this.message = error.error;
          this.type = 'error';
        },
      })
    );
  }

  cancelDeleteOperation() {
    this.showModalWarning = false;
    this.deleteInventory = null;
  }

  closeModalMessage() {
    this.showModalMessage = false;
    this.message = '';
    this.type = 'success';
  }
}
