import { Component, OnDestroy, OnInit } from '@angular/core';
import { OrderDTO } from '../../model/order/order-dto.model';
import { InventoryOrderService } from '../../service/inventory-order.service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { ImprovedPaginationComponent } from '../../improved-pagination/improved-pagination.component';
import { ChangePageSizeComponent } from '../../change-page-size/change-page-size.component';
import { SearchComponent } from '../../search/search.component';
import { SearchDateComponent } from '../../sub-component/search-date/search-date.component';
import { DateRange } from '../../model/order/date-range.model';
import { RouterModule } from '@angular/router';
import { DeleteInventoryUserWarningModalComponent } from '../../inventory-user-management/delete-inventory-user-warning-modal/delete-inventory-user-warning-modal.component';
import { MessageModalComponent } from '../../message-modal/message-modal.component';
import { AuthenticationService } from '../../service/authentication.service';

@Component({
  selector: 'app-inventory-order',
  standalone: true,
  imports: [
    CommonModule,
    ImprovedPaginationComponent,
    ChangePageSizeComponent,
    SearchComponent,
    SearchDateComponent,
    RouterModule,
    DeleteInventoryUserWarningModalComponent,
    MessageModalComponent,
  ],
  templateUrl: './inventory-order.component.html',
  styleUrl: './inventory-order.component.css',
})
export class InventoryOrderComponent implements OnInit, OnDestroy {
  inventoryOrders: OrderDTO[] = [];
  subscriptions: Subscription[] = [];
  pageNum: number = 1;
  currentLoginUser = '';
  sortField = 'createdTime';
  sortDir = 'asc';
  totalItems = 0;
  totalPage = 0;
  //Delete Warning
  message = 'Are you sure to delete this order';
  showModalWarning = false;
  deleteOrder: OrderDTO | null = null;
  //Response Warning
  messageRespone = '';
  type: 'success' | 'error' = 'success';
  showModalMessage = false;

  constructor(
    private inventoryOrderService: InventoryOrderService,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.inventoryOrderService.inventoryOrders$.subscribe((response) => {
        this.inventoryOrders = response;
      })
    );

    this.subscriptions.push(
      this.inventoryOrderService.pageNum$.subscribe((response) => {
        this.pageNum = this.pageNum;
      })
    );

    this.subscriptions.push(
      this.inventoryOrderService.totalItems$.subscribe((response) => {
        this.totalItems = response;
      })
    );

    this.subscriptions.push(
      this.inventoryOrderService.totalPage$.subscribe((response) => {
        this.totalPage = response;
      })
    );

    const currentLogginFromAuthenticationService =
      this.authenticationService.getUsernameFromToken();
    if (currentLogginFromAuthenticationService) {
      this.currentLoginUser = currentLogginFromAuthenticationService;
    }

    //console.log('Cuurent Login User:' + this.currentLoginUser);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  onPageNumChange(event: any) {
    this.inventoryOrderService.setPageNum(event);
  }

  onPageSizeChange(event: any) {
    this.inventoryOrderService.setPageSize(event);
  }

  onChangeSort(sortField: string) {
    if (this.sortField === sortField) {
      this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = sortField;
      this.sortDir = 'asc';
    }
    this.inventoryOrderService.setSortFieldAndSortDir(
      this.sortField,
      this.sortDir
    );
  }

  onKeyWord(keyWord: string) {
    this.inventoryOrderService.setKeyWord(keyWord);
  }
  onSearchDate(event: DateRange) {
    console.log('StartDate:' + event.startDate);
    console.log('EndDate:' + event.endDate);
    this.inventoryOrderService.setFromAndTo(event.startDate, event.endDate);
  }

  openModalWarning(item: OrderDTO) {
    this.showModalWarning = true;
    this.deleteOrder = item;
  }

  confirmDelete() {
    console.log('Confirm Delete');
    if (this.deleteOrder) {
      this.subscriptions.push(
        this.inventoryOrderService
          .deleteOrder(this.deleteOrder.orderId)
          .subscribe({
            next: (response) => {
              console.log(response);
              this.messageRespone = 'Successful delete Order';
              this.type = 'success';
              this.deleteOrder = null;
              this.showModalWarning = false;
              this.showModalMessage = true;
            },
            error: (error) => {
              console.log(error);
              (this.messageRespone = error.error), (this.type = 'error');
              this.deleteOrder = null;
              this.showModalWarning = false;
              this.showModalMessage = true;
            },
          })
      );
    }
  }

  cancelDelete() {
    this.deleteOrder = null;
    this.showModalWarning = false;
  }

  closeModalResponse() {
    this.message = '';
    this.type = 'success';
    this.showModalMessage = false;
    this.inventoryOrderService.setPageNum(1);
  }
}
