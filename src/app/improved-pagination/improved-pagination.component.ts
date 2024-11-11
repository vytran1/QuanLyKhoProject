import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-improved-pagination',
  standalone: true,
  imports: [],
  templateUrl: './improved-pagination.component.html',
  styleUrl: './improved-pagination.component.css',
})
export class ImprovedPaginationComponent {
  @Input()
  pageNum: number = 0;

  @Input()
  totalPage: number = 0;

  @Input()
  totalItems: number = 0;

  @Output()
  pageChange = new EventEmitter();

  goToPreviousPage(): void {
    if (this.pageNum > 1) {
      this.pageNum--;
      this.pageChange.emit(this.pageNum);
    }
  }

  goToNextPage(): void {
    if (this.pageNum < this.totalPage) {
      this.pageNum++;
      this.pageChange.emit(this.pageNum);
    }
  }

  goToPage(page: number): void {
    this.pageNum = page;
    this.pageChange.emit(this.pageNum);
  }

  generatePages(): number[] {
    return Array.from({ length: this.totalPage }, (_, i) => i + 1);
  }
}
