import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthenticationService } from '../service/authentication.service';
import { ForgotPasswordRequest } from '../model/auth/forgot-password-request.model';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css',
})
export class ForgotPasswordComponent implements OnInit {
  form: any;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService
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
      this.authService.forgotPassword(request).subscribe({
        next: (response) => {
          console.log(response);
          this.successMessage =
            'We have send to your email a link to reset your password';
        },
        error: (error) => {
          if (error.status === 400) {
            this.errorMessage = 'Your account email not exist in out system';
          } else {
            this.errorMessage = ' Internal Server Error';
          }
        },
      });
    } else {
      console.log('Invalid');
    }
  }
}
