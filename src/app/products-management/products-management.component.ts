import { Component, OnDestroy, OnInit } from '@angular/core';
import { Product } from '../model/products/products.model';
import { ProductsManagementService } from '../service/products-management.service';
import { Subscription } from 'rxjs';
import { ImprovedPaginationComponent } from '../improved-pagination/improved-pagination.component';
import { CommonModule } from '@angular/common';
import { SearchComponent } from '../search/search.component';
import { ChangePageSizeComponent } from '../change-page-size/change-page-size.component';
import { ProductsModalDetailsComponent } from './products-modal-details/products-modal-details.component';
import { DeleteInventoryUserWarningModalComponent } from '../inventory-user-management/delete-inventory-user-warning-modal/delete-inventory-user-warning-modal.component';
import { MessageModalComponent } from '../message-modal/message-modal.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-products-management',
  standalone: true,
  imports: [
    ImprovedPaginationComponent,
    CommonModule,
    SearchComponent,
    ChangePageSizeComponent,
    ProductsModalDetailsComponent,
    DeleteInventoryUserWarningModalComponent,
    MessageModalComponent,
    RouterModule,
  ],
  templateUrl: './products-management.component.html',
  styleUrl: './products-management.component.css',
})
export class ProductsManagementComponent implements OnInit, OnDestroy {
  products: Product[] = [];
  subscriptions: Subscription[] = [];
  //Pagination
  pageNum = 1;
  pageSize = 2;
  sortField = 'productName';
  sortDir = 'asc';
  totalPage = 0;
  totalItems = 0;

  //Detail
  selectedProduct: Product | null = null;
  showModal = false;

  //Delete Warning Modal
  showModalWarning = false;
  deleteProduct: Product | null = null;
  messageDelete = 'Are you sure to delete this modal';

  //Response Message Modal
  showModalMessage = false;
  message = '';
  type: 'success' | 'error' = 'success';

  constructor(private productMnService: ProductsManagementService) {}

  ngOnInit(): void {
    //this.loadData();

    this.subscriptions.push(
      this.productMnService.products$.subscribe((response) => {
        this.products = response;
      })
    );

    this.subscriptions.push(
      this.productMnService.pageNum$.subscribe((response) => {
        this.pageNum = response;
      })
    );
    this.subscriptions.push(
      this.productMnService.totalPage$.subscribe((response) => {
        this.totalPage = response;
      })
    );
    this.subscriptions.push(
      this.productMnService.totalItems$.subscribe((response) => {
        this.totalItems = response;
      })
    );
  }

  loadData() {
    this.subscriptions.push(
      this.productMnService
        .getProductsByPage(1, 2, 'productName', 'asc')
        .subscribe({
          next: (response) => {
            console.log('Products Load');
            console.log(response);
            if (response.body) {
              this.products = response.body.inventoryDTOs as Product[];
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
    this.productMnService.setPageNum(pageNum);
  }
  changeSort(sortField: string) {
    if (this.sortField === sortField) {
      this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = sortField;
      this.sortDir = 'asc';
    }
    this.productMnService.setSortFieldAndSortDir(
      this.sortField,
      this.sortDir
    );
  }

  onSearch(event: any) {
    this.productMnService.setKeyWord(event);
  }

  onPageSizeChange(event: any) {
    this.productMnService.setPageSize(event);
  }
  showModalDetails(selectedProduct: Product) {
    this.selectedProduct = selectedProduct;
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  openModalWarningDelete(item: Product) {
    this.deleteProduct = item;
    this.showModalWarning = true;
  }

  confirmDeleteOperation() {
    let productId = this.deleteProduct?.id;
    this.subscriptions.push(
      this.productMnService.deleteProduct(productId).subscribe({
        next: (response) => {
          console.log(response);
          this.cancelDeleteOperation();
          this.showModalMessage = true;
          this.message = 'Success Delete Product';
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
    this.deleteProduct = null;
  }

  closeModalMessage() {
    this.showModalMessage = false;
    this.message = '';
    this.type = 'success';
  }
}
