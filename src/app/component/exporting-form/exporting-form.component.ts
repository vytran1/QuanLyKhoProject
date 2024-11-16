import { Component, OnDestroy, OnInit } from '@angular/core';
import { ExportingFormDTO } from '../../model/exporting_form/exporting-form-dto.model';
import { Subscription } from 'rxjs';
import { ExportingFormService } from '../../service/exporting-form.service';
import { SearchComponent } from '../../search/search.component';
import { RouterModule } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';
import { ChangePageSizeComponent } from '../../change-page-size/change-page-size.component';
import { ImprovedPaginationComponent } from '../../improved-pagination/improved-pagination.component';

@Component({
  selector: 'app-exporting-form',
  standalone: true,
  imports: [
    SearchComponent,
    RouterModule,
    DatePipe,
    ChangePageSizeComponent,
    ImprovedPaginationComponent,
    CommonModule,
  ],
  templateUrl: './exporting-form.component.html',
  styleUrl: './exporting-form.component.css',
})
export class ExportingFormComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  exportingForms: ExportingFormDTO[] = [];
  pageNum: number = 1;
  sortField: string = 'createdTime';
  sortDir: string = 'desc';
  totalItems: number = 0;
  totalPage: number = 0;

  constructor(private exportingFormService: ExportingFormService) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.exportingFormService.exportingForm$.subscribe((response) => {
        if (response) {
          this.exportingForms = response;
        }
      })
    );

    this.subscriptions.push(
      this.exportingFormService.pageNum$.subscribe((response) => {
        if (response) {
          this.pageNum = response;
        }
      })
    );

    this.subscriptions.push(
      this.exportingFormService.totalItems$.subscribe((response) => {
        if (response) {
          this.totalItems = response;
        }
      })
    );

    this.subscriptions.push(
      this.exportingFormService.totalPage$.subscribe((response) => {
        if (response) {
          this.totalPage = response;
        }
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  onKeyWord(keyWord: string) {
    this.exportingFormService.setKeyWord(keyWord);
  }

  onChangeSort(sortField: string) {
    if (this.sortField === sortField) {
      this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = sortField;
      this.sortDir = 'asc';
    }
    this.exportingFormService.setSortFieldAndSortDir(
      this.sortField,
      this.sortDir
    );
  }

  onPageSizeChange(pageSize: number) {
    this.exportingFormService.setPageSize(pageSize);
  }

  onPageNumChange(pageNum: number) {
    this.exportingFormService.setPageNum(pageNum);
  }
}
