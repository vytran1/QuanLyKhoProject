import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  emailDuplicate,
  identityNumberDuplicate,
  userIdDuplicate,
} from '../../validators/create-user.validator';
import { InventoryUserManagementService } from '../../service/inventory-user-management.service';
import { InventoryUser } from '../../model/inventory-user/inventory-user.model';
import { MessageModalComponent } from '../../message-modal/message-modal.component';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-inventory-user-create-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MessageModalComponent],
  templateUrl: './inventory-user-create-form.component.html',
  styleUrl: './inventory-user-create-form.component.css',
})
export class InventoryUserCreateFormComponent implements OnInit {
  form: any;
  //Modal
  showModal: boolean = false;
  message: string | null = '';
  type: 'success' | 'error' = 'success';
  subscriptions: Subscription[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private inventoryUserService: InventoryUserManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      userId: [
        '',
        {
          validators: [
            Validators.required,
            Validators.minLength(9),
            Validators.maxLength(30),
          ],

          asyncValidators: [userIdDuplicate(this.inventoryUserService)],
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
      email: [
        '',
        {
          validators: [Validators.required, Validators.email],
          asyncValidators: [emailDuplicate(this.inventoryUserService)],
          updateOn: 'blur',
        },
      ],
      phoneNumber: [
        '',
        [Validators.required, Validators.pattern('^[0-9]{10}$')],
      ],
      identityNumber: [
        '',
        {
          validators: [Validators.required, Validators.pattern('^[0-9]{12}$')],
          asyncValidators: [identityNumberDuplicate(this.inventoryUserService)],
          updateOn: 'blur',
        },
      ],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      let newInventoryUser: InventoryUser = this.form.value;
      console.log('New Inventory User');
      console.log(newInventoryUser);
      this.subscriptions.push(
        this.inventoryUserService.createUser(newInventoryUser).subscribe({
          next: (response) => {
            if (response.status === 200) {
              console.log('Inventory User Created');
              this.message = 'Successfully Creating User';
              this.type = 'success';
              this.showModal = true;
            }
          },
          error: (error) => {
            this.message = error.error;
            this.type = error;
            this.showModal = true;
          },
        })
      );
    } else {
      console.log('Form is invalid');
    }
  }

  closeModal() {
    this.showModal = false;
    if (this.type === 'success') {
      this.inventoryUserService.setPageNum(1);
      this.router.navigate(['/inventory/inventory_users']);
    }
  }
}
