import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AccountInformationService } from '../../service/account-information.service';

@Component({
  selector: 'app-update-image',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './update-image.component.html',
  styleUrl: '../../../styles/share-style.css',
})
export class UpdateImageComponent implements OnInit {
  form: any;
  file: File | null = null;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  previewUrl: string | null = null;
  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountInformationService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({});
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const selectedFile = input.files[0];
      const maxSize = 2 * 1024 * 1024;
      if (selectedFile.size > maxSize) {
        this.errorMessage = 'Kích cỡ ảnh quá lớn';
        this.file = null;
      } else {
        this.file = selectedFile;
        this.errorMessage = null;

        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.previewUrl = e.target.result;
        };
        reader.readAsDataURL(selectedFile);
      }
    }
  }

  onSubmit() {
    if (this.file) {
      this.accountService.updatePersonalImage(this.file).subscribe({
        next: (response) => {
          if (response.status === 200) {
            this.successMessage = 'Cập nhật ảnh thành công';
            this.errorMessage = null;
            this.resetForm();
            this.loadProfileImageFromServer();
          } else {
            this.errorMessage = 'Đã xảy ra lỗi Vui lòng thử lại sau';
          }
        },
        error: (error) => {
          console.log(error);
          this.successMessage = null;
          if (error.status === 400) {
            this.errorMessage = 'Chỉ chấp nhận ảnh';
          } else {
            this.errorMessage = 'Đã xảy ra lỗi Vui lòng thử lại sau';
          }
        },
      });
    }
  }

  public resetForm() {
    this.form.reset();
    this.file = null;
    this.previewUrl = null;
  }

  loadProfileImageFromServer() {
    this.accountService.getPersonalImage().subscribe({
      next: (imageBlob) => {
        const objectUrl = URL.createObjectURL(imageBlob);
        this.accountService.setProfileImage(objectUrl);
      },
      error: (error) => {
        console.error('Error fetching profile image:', error);
      },
    });
  }
}
