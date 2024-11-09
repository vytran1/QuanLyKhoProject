import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormBuilder,
  Validators,
  ValueChangeEvent,
  FormGroup,
  FormControl,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
import {
  HttpClientModule,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { InventoryUser } from '../model/inventory-user/inventory-user.model';
import { Subscription } from 'rxjs';
import { AuthRequest } from '../model/auth/auth-request.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit, OnDestroy {
  form: FormGroup = new FormGroup({});
  public showLoading!: boolean;
  public subscriptions: Subscription[] = [];
  isError = false;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private authService: AuthenticationService,
    private toastService: ToastrService
  ) {}

  ngOnInit(): void {
    if (this.authService.isLogged()) {
      this.router.navigateByUrl('/inventory/dashboard');
    } else {
      this.router.navigateByUrl('/login');
    }

    this.form = this.fb.group({
      username: [
        ' ',
        {
          validators: [Validators.required, Validators.email],
        },
      ],
      password: [
        ' ',
        {
          validators: [Validators.required, Validators.minLength(5)],
        },
      ],
    });

    const queryParamsSubscription = this.activeRoute.queryParams.subscribe(
      (params) => {
        if (params['error'] === '401') {
          this.isError = true;
        }
      }
    );
    this.subscriptions.push(queryParamsSubscription);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subcriptiom) => {
      subcriptiom.unsubscribe();
    });
  }
  login() {
    // let username = this.form.get('username')?.value;
    // let password = this.form.get('password')?.value;
    // console.log(username, password);
    console.log(this.form.value);
    this.form.reset();
  }

  public onLogin() {
    let authRequest: AuthRequest = this.form.value;
    this.showLoading = true;
    this.subscriptions.push(
      this.authService.login(authRequest).subscribe(
        (response: HttpResponse<any>) => {
          console.log(response);
          const token = response.body.accessToken;
          this.authService.saveToken(token);
          this.form.reset();
          this.toastService.success('Login successful!', 'Success', {
            timeOut: 2000,
          });
          setTimeout(() => {
            this.router.navigateByUrl('/inventory/dashboard');
          }, 2000);
          this.showLoading = false;
        },
        (error: HttpErrorResponse) => {
          this.router.navigate(['/login'], { queryParams: { error: '401' } });
          console.log(error);
        }
      )
    );
  }
}
