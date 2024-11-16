import { Component, OnDestroy, OnInit } from '@angular/core';
import { InventoryProduct } from '../model/product_inventory/inventory-product.model';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { InventoryProductService } from '../service/inventory-product.service';

@Component({
  selector: 'app-find-stock',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './find-stock.component.html',
  styleUrl: './find-stock.component.css',
})
export class FindStockComponent implements OnInit, OnDestroy {
  inventoryProduct: InventoryProduct | null = null;
  subscriptions: Subscription[] = [];
  form: any;
  defaultMessage: string = 'Please provide product ID';

  constructor(
    private formBuilder: FormBuilder,
    private inventoryProductService: InventoryProductService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      product_id: [
        '',
        [
          Validators.required,
          Validators.pattern('^[0-9]*$'),
          Validators.min(1),
        ],
      ],
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  onSearch() {
    if (this.form.valid) {
      const productId = this.form.get('product_id')?.value;
      this.subscriptions.push(
        this.inventoryProductService.findStock(productId).subscribe({
          next: (response) => {
            console.log(response);
            if (response.status === 204) {
              this.inventoryProduct = null;
              this.defaultMessage = 'We have not imported this product';
            }
            if (response.status === 200) {
              this.defaultMessage = '';
              this.inventoryProduct = new InventoryProduct();
              this.inventoryProduct.productName = response.body.productName;
              this.inventoryProduct.quantityList = response.body.results;
            }
          },
          error: (error) => {
            if (error.status === 404) {
              this.defaultMessage = error.error;
            }
          },
        })
      );
    }
  }
}
