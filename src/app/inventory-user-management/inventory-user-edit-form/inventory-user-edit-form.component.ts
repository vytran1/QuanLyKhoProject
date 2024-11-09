import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { identityNumberDuplicate } from '../../validators/create-user.validator';
import { InventoryUserManagementService } from '../../service/inventory-user-management.service';
import { MessageModalComponent } from '../../message-modal/message-modal.component';
import { InventoryUser } from '../../model/inventory-user/inventory-user.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-inventory-user-edit-form',
  standalone: true,
  imports: [MessageModalComponent, ReactiveFormsModule],
  templateUrl: './inventory-user-edit-form.component.html',
  styleUrl: './inventory-user-edit-form.component.css',
})
export class InventoryUserEditFormComponent implements OnInit, OnDestroy {
  userId!: string;
  form: any;
  userEdit!: InventoryUser;
  subscriptions: Subscription[] = [];
  //Modal
  showModal: boolean = false;
  message: string | null = null;
  type: 'success' | 'error' = 'success';

  constructor(
    private activeRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private inventoryUserService: InventoryUserManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userId = this.activeRoute.snapshot.paramMap.get('id')!;

    this.form = this.formBuilder.group({
      userId: [''],
      email: [''],
      identityNumber: [
        '',
        {
          validators: [Validators.required, Validators.pattern('^[0-9]{12}$')],
          updateOn: 'blur',
        },
      ],
      firstName: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
        ],
      ],
      lastName: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
        ],
      ],
      phoneNumber: [
        '',
        [Validators.required, Validators.pattern('^[0-9]{10}$')],
      ],
    });
    this.subscriptions.push(
      this.inventoryUserService.getUserById(this.userId).subscribe({
        next: (response) => {
          console.log(response);
          this.form.patchValue({
            userId: response.body.userId,
            email: response.body.email,
            identityNumber: response.body.identityNumber,
            firstName: response.body.firstName,
            lastName: response.body.lastName,
            phoneNumber: response.body.phoneNumber,
          });
        },
        error: (error) => {
          console.log(error);
        },
      })
    );
  }
  onSubmit() {
    if (this.form.valid) {
      let inventoryUser: InventoryUser = this.form.value;
      // console.log(inventoryUser);
      // console.log('Valid Form Edit');
      this.inventoryUserService.updateUser(inventoryUser).subscribe({
        next: (response) => {
          this.message = 'Success update user information';
          this.type = 'success';
          this.showModal = true;
        },
        error: (error) => {
          console.log(error);
          this.message = error.error;
          this.type = 'error';
          this.showModal = true;
        },
      });
    } else {
      console.log('Invalid Form Edit');
    }
  }

  closeModal() {
    if (this.type === 'success') {
      this.router.navigate(['/inventory/inventory_users']);
      this.showModal = false;
    } else {
      this.showModal = false;
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
