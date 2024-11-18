import { Component, OnDestroy, OnInit } from '@angular/core';
import { MessageModalComponent } from '../../message-modal/message-modal.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductsManagementService } from '../../service/products-management.service';
import { Product } from '../../model/product/productsManagement.model';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products-edit-form',
  standalone: true,
  imports: [
    MessageModalComponent,
    ReactiveFormsModule,
    RouterModule,
    CommonModule,
  ],
  templateUrl: './products-edit-form.component.html',
  styleUrl: './products-edit-form.component.css',
})
export class ProductsEditFormComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  //Form
  form: any;
  product: Product | null = null;
  //Modal
  showModal = false;
  message = '';
  type: 'success' | 'error' = 'success';

  constructor(
    private formBuilder: FormBuilder,
    private activeRouter: ActivatedRoute,
    private productService: ProductsManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    let productId = this.activeRouter.snapshot.paramMap.get('id');

    this.form = this.formBuilder.group({
      id: ['', Validators.required],
      name: ['', [Validators.required, Validators.minLength(4)]],
      alias: ['', [Validators.required, Validators.minLength(4)]],
      description: ['', [Validators.required, Validators.minLength(20), Validators.maxLength(1024)]],
      unit: ['', [Validators.required, Validators.minLength(3)]],
      price: ['', [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*(\\.[0-9]+)?$')]],
      brandId: [{ value: '', disabled: true }, Validators.required],
      categoryId: [{ value: '', disabled: true }, Validators.required],
      enabled: ['', [Validators.required]],
    });

    this.loadData(productId);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  loadData(productId: any) {
    this.subscriptions.push(
      this.productService.getProductById(productId).subscribe({
        next: (response) => {
          this.product = response.body as Product;
          this.form.patchValue({
            id: this.product.id,
            name: this.product.name,
            alias: this.product.alias,
            description: this.product.description,
            unit: this.product.unit,
            price: this.product.price,
            brandId: this.product.brandId,
            categoryId: this.product.categoryId,
            enabled: this.product.enabled
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

  updateProduct() {
    console.log(this.form.value);
    let requestBody = this.form.value as Product;
    this.subscriptions.push(
      this.productService.updateProduct(requestBody).subscribe({
        next: (response) => {
          this.message = 'Successfully update product information';
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
      this.productService.setPageNum(1);
      this.router.navigate(['/inventory/products']);
    } else {
      this.message = '';
      this.type = 'success';
      this.showModal = false;
    }
  }
}
