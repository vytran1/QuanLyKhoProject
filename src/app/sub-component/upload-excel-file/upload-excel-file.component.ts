import { Component, EventEmitter, Output } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-upload-excel-file',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './upload-excel-file.component.html',
  styleUrl: './upload-excel-file.component.css',
})
export class UploadExcelFileComponent {
  selectedFile: File | null = null;

  @Output()
  fileEmit = new EventEmitter<File>();

  @Output()
  closeEmit = new EventEmitter();

  onFileChange(event: any) {
    const file = event.target.files[0];
    const allowedExtensions = /(\.xls|\.xlsx)$/i;

    if (!allowedExtensions.exec(file.name)) {
      alert('Please upload a valid Excel file (with .xls or .xlsx extension).');
      event.target.value = ''; // Clear the input
      return;
    }

    this.selectedFile = file;
  }

  onSubmit(event: any) {
    event.preventDefault();
    if (this.selectedFile) {
      console.log('Submit Form Sub Component');

      this.fileEmit.emit(this.selectedFile);
      this.closeForm();
    } else {
      alert('Please Selected A File');
    }
  }

  closeForm() {
    this.closeEmit.emit();
  }
}
