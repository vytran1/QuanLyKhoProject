import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges } from '@angular/core';
import { Product } from '../../model/product/productsManagement.model';
import { BehaviorSubject } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products-modal-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './products-modal-details.component.html',
  styleUrl: './products-modal-details.component.css',
})
export class ProductsModalDetailsComponent {
  @Input()
  showModal: boolean = false;

  @Input()
  product: Product | null = null;

  @Output()
  closeModalEvent = new EventEmitter<void>();

  closeModal() {
    this.closeModalEvent.emit();
  }

  // Thêm ngOnChanges để kiểm tra giá trị product
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['product']) {
      console.log('Product data:', this.product);
    }
  }
}
