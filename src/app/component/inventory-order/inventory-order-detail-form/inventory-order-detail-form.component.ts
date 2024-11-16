import { Component, OnDestroy, OnInit } from '@angular/core';
import { OrderInformationDetailDTO } from '../../../model/order/order_information_detail.model';
import { Subscription } from 'rxjs';
import { InventoryOrderService } from '../../../service/inventory-order.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-inventory-order-detail-form',
  standalone: true,
  imports: [],
  templateUrl: './inventory-order-detail-form.component.html',
  styleUrl: './inventory-order-detail-form.component.css',
})
export class InventoryOrderDetailFormComponent implements OnInit, OnDestroy {
  orderInformationDetailDTO: OrderInformationDetailDTO | null = null;
  subscriptions: Subscription[] = [];

  constructor(
    private inventoryOrderService: InventoryOrderService,
    private activeRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    let orderId = this.activeRoute.snapshot.paramMap.get('id')!;
    this.subscriptions.push(
      this.inventoryOrderService.getDetailOrderById(orderId).subscribe({
        next: (response) => {
          console.log(response);
          this.orderInformationDetailDTO = response.body;
        },
        error: (error) => {
          console.log(error);
        },
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  returnHome() {
    this.router.navigate(['/inventory/inventory_order']);
  }
}
