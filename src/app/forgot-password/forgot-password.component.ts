import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthenticationService } from '../service/authentication.service';
import { ForgotPasswordRequest } from '../model/auth/forgot-password-request.model';
import {
  NgxSpinnerComponent,
  NgxSpinnerModule,
  NgxSpinnerService,
} from 'ngx-spinner';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, NgxSpinnerModule],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css',
})
export class ForgotPasswordComponent implements OnInit {
  form: any;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  isSubmitting = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService,
    private spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      console.log(this.form.value);
      let request: ForgotPasswordRequest = this.form.value;
      this.isSubmitting = true;
      this.spinner.show();
      this.authService.forgotPassword(request).subscribe({
        next: (response: any) => {
          console.log(response);
          if (response && response.body.message) {
            this.successMessage = response.body.message;
          }
        },
        error: (error) => {
          console.log(error);
          if (error.status === 400) {
            this.errorMessage = 'Your account email not exist in out system';
          } else {
            this.errorMessage = ' Internal Server Error';
          }
        },
        complete: () => {
          this.isSubmitting = false;
          this.spinner.hide();
        },
      });
    } else {
      console.log('Invalid');
    }
  }
}
