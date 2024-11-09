import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  matchValidator,
  passwordValidator,
} from '../validators/password.validator';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css',
})
export class ResetPasswordComponent implements OnInit {
  form: any;
  errorMessage: string | null = null;
  token: string | null = null;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private authService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      newPassword: ['', [Validators.required, passwordValidator()]],
      confirmPassword: [
        '',
        [Validators.required, matchValidator('newPassword')],
      ],
    });

    this.token = this.route.snapshot.queryParamMap.get('token');
  }

  onSubmit() {
    if (this.form.valid) {
      const request = {
        newPassword: this.form.get('newPassword')?.value,
      };
      if (this.token) {
        this.authService.resetPassword(request, this.token).subscribe({
          next: (response) => {
            this.errorMessage = null;
            this.router.navigate(['/login']);
          },
          error: (error) => {
            if (error.status === 400) {
              this.errorMessage = 'Token reset password is not valid';
            } else {
              this.errorMessage = 'Internal Server Error';
            }
          },
        });
      } else {
        this.errorMessage = 'Invalid token';
        this.form.reset();
      }
    }
  }
}
