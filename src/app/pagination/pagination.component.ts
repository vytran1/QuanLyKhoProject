import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css',
})
export class PaginationComponent {
  @Input()
  pageNum: number = 1;

  @Input()
  totalPages: number = 0;

  @Input()
  totalItems: number = 0;

  @Output() pageChange = new EventEmitter<number>();

  goToPreviousPage(): void {
    if (this.pageNum > 1) {
      this.pageNum--;
      this.pageChange.emit(this.pageNum);
    }
  }
  goToNextPage(): void {
    if (this.pageNum < this.totalPages) {
      this.pageNum++;
      this.pageChange.emit(this.pageNum);
    }
  }
}
