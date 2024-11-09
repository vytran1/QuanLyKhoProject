import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-change-page-size',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './change-page-size.component.html',
  styleUrl: './change-page-size.component.css',
})
export class ChangePageSizeComponent implements OnInit {
  pageSizeForm: any;

  @Output()
  pageSizeChange = new EventEmitter<number>();

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.pageSizeForm = this.formBuilder.group({
      pageSize: [2],
    });
  }

  onPageSizeChange() {
    const pageSize = this.pageSizeForm.get('pageSize').value;
    if (pageSize) {
      this.pageSizeChange.emit(pageSize);
    }
  }
}
