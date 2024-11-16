import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { OrderWithoutIPF } from '../../model/order/order-without-imform.model';
import { InventoryOrderService } from '../../service/inventory-order.service';
import { Subscription } from 'rxjs';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService } from '../../service/authentication.service';

@Component({
  selector: 'app-order-without-importingform-list',
  standalone: true,
  imports: [],
  templateUrl: './order-without-importingform-list.component.html',
  styleUrl: './order-without-importingform-list.component.css',
})
export class OrderWithoutImportingformListComponent
  implements OnInit, OnDestroy
{
  subscriptions: Subscription[] = [];
  orderWithoutIDF: OrderWithoutIPF[] = [];
  currentLogin: string | null = '';

  @Output()
  eventEmitter = new EventEmitter();

  constructor(
    private inventoryOrderService: InventoryOrderService,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    if (this.authenticationService.getUsernameFromToken()) {
      this.currentLogin = this.authenticationService.getUsernameFromToken();
    }

    this.subscriptions.push(
      this.inventoryOrderService.getOrdersWithoutImportingForm().subscribe({
        next: (response) => {
          console.log(response);
          this.orderWithoutIDF = response.body;
        },
        error: (error) => {
          console.log(error);
        },
      })
    );
  }

  closeModal() {
    this.eventEmitter.emit();
  }

  ngOnDestroy(): void {}

  chooseOrder(orderId: string) {
    this.router.navigate(['/inventory/create_importing_form', orderId]);
  }
}
