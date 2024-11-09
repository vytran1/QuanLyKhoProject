import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css',
})
export class SearchComponent implements OnInit {
  searchForm: any;

  @Output()
  searchEvent = new EventEmitter<string>();

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      keyWord: ['', [Validators.required, Validators.pattern('\\S+')]],
    });
  }

  onSearch() {
    const keyWord = this.searchForm.get('keyWord').value;
    if (this.searchForm.valid && keyWord.trim()) {
      this.searchEvent.emit(keyWord);
    }
  }

  clearSearch() {
    this.searchForm.reset();
    this.searchEvent.emit('');
  }
}
