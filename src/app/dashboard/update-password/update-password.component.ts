import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  matchValidator,
  passwordValidator,
} from '../../validators/password.validator';
import { Router } from '@angular/router';
import { AccountInformationService } from '../../service/account-information.service';
import { AuthenticationService } from '../../service/authentication.service';

@Component({
  selector: 'app-update-password',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './update-password.component.html',
  styleUrl: '../../../styles/share-style.css',
})
export class UpdatePasswordComponent implements OnInit {
  form: any;
  errorMessage: string | null = null;
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private accountService: AccountInformationService,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, passwordValidator()]],
      confirmPassword: [
        '',
        [Validators.required, matchValidator('newPassword')],
      ],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      console.log('Invalid');
      console.log(this.form.value);
      let requestData = this.form.value;
      this.accountService.updatePassword(requestData).subscribe({
        next: (response) => {
          console.log('Update Password Successfully');
          this.authenticationService.logout();
          this.router.navigate(['/login']);
        },
        error: (error) => {
          if (error.status === 400) {
            this.errorMessage =
              'Mật khẩu mới không khớp với mật khẩu bạn đã đăng ký';
          } else {
            this.errorMessage = 'Đã xảy ra lỗi. Vui lòng thử lại';
          }
        },
      });
    } else {
      console.log('Invalid Data');
    }
  }
}
