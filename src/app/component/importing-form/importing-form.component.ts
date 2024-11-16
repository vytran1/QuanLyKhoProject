import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ImportingFormService } from '../../service/importing-form.service';
import { ImportingFormDTO } from '../../model/importing-form/importing-form-dto.model';
import { ImprovedPaginationComponent } from '../../improved-pagination/improved-pagination.component';
import { ChangePageSizeComponent } from '../../change-page-size/change-page-size.component';
import { SearchComponent } from '../../search/search.component';
import { CommonModule } from '@angular/common';
import { OrderWithoutImportingformListComponent } from '../../sub-component/order-without-importingform-list/order-without-importingform-list.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-importing-form',
  standalone: true,
  imports: [
    ImprovedPaginationComponent,
    ChangePageSizeComponent,
    SearchComponent,
    CommonModule,
    OrderWithoutImportingformListComponent,
    RouterModule,
  ],
  templateUrl: './importing-form.component.html',
  styleUrl: './importing-form.component.css',
})
export class ImportingFormComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  importingForms: ImportingFormDTO[] = [];
  sortField: string = 'createdTime';
  sortDir: string = 'asc';
  pageNum: number = 1;
  totalPage: number = 0;
  totalItems: number = 0;
  showOrderWithoutIDFList = false;

  constructor(private importingFormService: ImportingFormService) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.importingFormService.importingSubject$.subscribe((response) => {
        this.importingForms = response;
      })
    );

    this.subscriptions.push(
      this.importingFormService.pageNum$.subscribe((response) => {
        this.pageNum = response;
      })
    );

    this.subscriptions.push(
      this.importingFormService.totalPage$.subscribe((response) => {
        this.totalPage = response;
      })
    );

    this.subscriptions.push(
      this.importingFormService.totalItem$.subscribe((response) => {
        this.totalItems = response;
      })
    );
  }

  onPageNumChange(pageNum: number) {
    this.importingFormService.setPageNum(pageNum);
  }

  onPageSizeChange(pageSize: number) {
    this.importingFormService.setPageSize(pageSize);
  }

  onKeyWord(keyWord: string) {
    this.importingFormService.setKeyWord(keyWord);
  }

  onSortFieldChange(sortField: string) {
    if (this.sortField === sortField) {
      this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = sortField;
      this.sortDir = 'asc';
    }
    this.importingFormService.setSortFieldAndSortDir(
      this.sortField,
      this.sortDir
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  openOrderWithoutIDFList() {
    console.log('Open Modal');
    this.showOrderWithoutIDFList = true;
  }

  closeOrderWithoutIDFList() {
    this.showOrderWithoutIDFList = false;
  }
}
