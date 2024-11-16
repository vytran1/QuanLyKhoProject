import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { Product } from '../../model/product/product.model';
import { ProductForOrderService } from '../../service/product-for-order.service';
import { Subscription } from 'rxjs';
import { EventManager } from '@angular/platform-browser';
import { ImprovedPaginationComponent } from '../../improved-pagination/improved-pagination.component';
import { SearchComponent } from '../../search/search.component';

@Component({
  selector: 'app-product-list-modal',
  standalone: true,
  imports: [ImprovedPaginationComponent, SearchComponent],
  templateUrl: './product-list-modal.component.html',
  styleUrl: './product-list-modal.component.css',
})
export class ProductListModalComponent implements OnInit, OnDestroy {
  products: Product[] = [];
  subscriptions: Subscription[] = [];
  totalPage = 0;
  totalItems = 0;
  pageNum = 1;
  @Output()
  emitEvent = new EventEmitter();

  @Output()
  closeEvent = new EventEmitter();

  constructor(private productService: ProductForOrderService) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.productService.product$.subscribe((response) => {
        this.products = response;
      })
    );

    this.subscriptions.push(
      this.productService.totalPage$.subscribe((response) => {
        this.totalPage = response;
      })
    );

    this.subscriptions.push(
      this.productService.totalItems$.subscribe((response) => {
        this.totalItems = response;
      })
    );

    this.subscriptions.push(
      this.productService.pageNum$.subscribe((response) => {
        this.pageNum = response;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subcription) => subcription.unsubscribe());
  }

  onChoose(id: number) {
    this.emitEvent.emit(id);
  }

  closeModal() {
    this.closeEvent.emit();
  }

  onPageChange(pageNum: number) {
    this.productService.setPageNum(pageNum);
  }

  onKeyWord(keyWord: string) {
    this.productService.setKeyWord(keyWord);
  }
}
