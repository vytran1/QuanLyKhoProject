import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { InventoryProductForEPF } from '../../model/product_inventory/inventory-product-for-exporting-form';
import { Subscription } from 'rxjs';
import { ProductQuantity } from '../../model/product_inventory/product-quantity.model';
import { InventoryProductService } from '../../service/inventory-product.service';

@Component({
  selector: 'app-list-stock-by-inventory',
  standalone: true,
  imports: [],
  templateUrl: './list-stock-by-inventory.component.html',
  styleUrl: './list-stock-by-inventory.component.css',
})
export class ListStockByInventoryComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  inventoryProduct: InventoryProductForEPF | null = null;

  @Input()
  inventoryId: string = '';

  @Output()
  closeEmit = new EventEmitter();

  @Output()
  selectedStock = new EventEmitter<ProductQuantity>();

  constructor(private inventoryProductService: InventoryProductService) {}

  ngOnInit(): void {
    if (this.inventoryId) {
      this.subscriptions.push(
        this.inventoryProductService
          .findStockByInventoryId(this.inventoryId)
          .subscribe({
            next: (response) => {
              console.log(response);
              if (response.status === 200) {
                this.inventoryProduct = response.body as InventoryProductForEPF;
              } else if (response.status === 204) {
                this.inventoryProduct = null;
              }
            },
            error: (error) => {
              console.error(error);
            },
          })
      );
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  closeModal() {
    this.closeEmit.emit();
  }

  selectItem(item: ProductQuantity) {
    this.selectedStock.emit(item);
  }
}
