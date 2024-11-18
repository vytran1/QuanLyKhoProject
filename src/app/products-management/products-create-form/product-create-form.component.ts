import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Brand } from '../../model/product/brand.model';
import { Subscription } from 'rxjs';
import { Category } from '../../model/product/category.model';
import { selectValidator } from '../../validators/location.validator';
import { ProductsManagementService } from '../../service/products-management.service';
import { Product } from '../../model/product/productsManagement.model';
import { MessageModalComponent } from '../../message-modal/message-modal.component';
import { Router, RouterModule } from '@angular/router';
import { BrandCategoryService } from '../../service/brand-category.service';

@Component({
  selector: 'app-product-create-form',
  standalone: true,
  imports: [ReactiveFormsModule, MessageModalComponent, RouterModule],
  templateUrl: './product-create-form.component.html',
  styleUrl: './product-create-form.component.css',
})
export class ProductCreateFormComponent implements OnInit, OnDestroy {
  form: any;
  subscriptions: Subscription[] = [];
  brands: Brand[] = [];
  categories: Category[] = [];
  //Modal message
  showModal = false;
  type: 'success' | 'error' = 'success';
  message = '';

  constructor(
    private formBuilder: FormBuilder,
    private brand_categoryService: BrandCategoryService,
    private productService: ProductsManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: [
        '',
        {
          validators: [Validators.required, Validators.minLength(4)],
        },
      ],
      alias: [
        '',
        {
          validators: [Validators.required, Validators.minLength(4)],
        },
      ],
      description: [
        '',
        {
          validators: [Validators.required, Validators.minLength(20), Validators.maxLength(1024)]
        }
      ],
      unit: [
        '',
        {
          validators: [Validators.required, Validators.minLength(3), Validators.maxLength(20)]
        }
      ],
      price: [
        '',
        {
          validators: [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*(\\.[0-9]+)?$')]
        }
      ],
      enabled: [
        '',
        {
          validators: [Validators.required]
        }
      ],
      brandId: ['', [Validators.required, selectValidator]],

      categoryId: ['', [Validators.required, selectValidator]],
    });

    this.subscriptions.push(
      this.brand_categoryService.getBrands().subscribe({
        next: (response) => {
          this.brands = response.body;
        },
        error: (error) => {
          console.log(error);
        },
      })
    );

    this.subscriptions.push(
      this.form.get('brandId').valueChanges.subscribe((brandId: any) => {
        if (brandId && brandId !== '') {
          this.subscriptions.push(
            this.brand_categoryService.getCategories(brandId).subscribe({
              next: (response) => {
                this.categories = response.body;
                this.form.get('categoryId').enable();
                this.form.get('categoryId').setValue(''); // Reset state selection
              },
            })
          );
        }
      })
    );

    this.form.get('categoryId').disable();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  createProduct() {
    if (this.form.valid) {
      let requestBody: Product = this.form.value;
      console.log('Product Price:', requestBody.price);
      console.log('Product BrandId:', typeof requestBody.brandId);
      console.log('Product CategoryId:', typeof requestBody.categoryId);

      this.subscriptions.push(
        this.productService.createProduct(requestBody).subscribe({
          next: (response) => {
            console.log('Add Product');
            console.log(response);
            this.message = 'Success Add Product';
            this.type = 'success';
            this.showModal = true;
          },
          error: (error) => {
            console.log('Has Error When Create Product');
            console.log(error);
            this.message = error.error;
            this.type = 'error';
            this.showModal = true;
          },
        })
      );
    } else {
      console.log('Invalid');
    }
  }

  closeModalMessage() {
    if (this.type === 'success') {
      this.router.navigate(['/inventory/products']);
    } else {
      this.message = '';
      this.type = 'success';
      this.showModal = false;
    }
  }
}
