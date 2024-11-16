import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { dateRangeValidator } from '../../validators/inventory.validator';

@Component({
  selector: 'app-search-date',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './search-date.component.html',
  styleUrl: './search-date.component.css',
})
export class SearchDateComponent implements OnInit {
  form: any;

  @Output()
  emitEvent = new EventEmitter();

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        startDate: ['', Validators.required],
        endDate: ['', Validators.required],
      },
      { validators: dateRangeValidator } // Apply the custom validator here
    );
  }

  onSearchDate() {
    if (this.form.valid) {
      console.log(this.form.value);
      this.emitEvent.emit(this.form.value);
    } else {
      console.log('Invalid');
    }
  }

  // dateRangeValidator(
  //   control: AbstractControl
  // ): { [key: string]: boolean } | null {
  //   const startDate = control.get('startDate')?.value;
  //   const endDate = control.get('endDate')?.value;
  //   if (startDate && endDate && startDate > endDate) {
  //     return { invalidDateRange: true };
  //   }
  //   return null;
  // }
}
