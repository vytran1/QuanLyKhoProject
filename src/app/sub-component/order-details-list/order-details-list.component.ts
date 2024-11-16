import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { InventoryOrderService } from '../../service/inventory-order.service';
import { Subscription } from 'rxjs';
import { OrderDetailDTO } from '../../model/order/order-detail-dto.model';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-order-details-list',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './order-details-list.component.html',
  styleUrl: './order-details-list.component.css',
})
export class OrderDetailsListComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  @Input()
  orderId: string = '';
  orderDetails: OrderDetailDTO[] = [];

  @Output()
  emitEvent = new EventEmitter();

  @Output()
  emitSelectItem = new EventEmitter<OrderDetailDTO>();

  constructor(private inventoryOrderService: InventoryOrderService) {}

  ngOnInit(): void {
    if (this.orderId) {
      this.subscriptions.push(
        this.inventoryOrderService
          .getOrderDetailsByOrderId(this.orderId)
          .subscribe({
            next: (response) => {
              console.log(response);
              this.orderDetails = response.body;
            },
            error: (error) => {
              console.log(error);
            },
          })
      );
    }
  }

  selectItem(item: OrderDetailDTO) {
    this.emitSelectItem.emit(item);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
  closeModal() {
    this.emitEvent.emit();
  }
}
