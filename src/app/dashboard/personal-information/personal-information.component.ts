import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AccountInformationService } from '../../service/account-information.service';
import { Account } from '../../model/account/account.model';
@Component({
  selector: 'app-personal-information',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './personal-information.component.html',
  styleUrl: '../../../styles/share-style.css',
})
export class PersonalInformationComponent implements OnInit {
  form: any;
  isEditMode: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountInformationService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      userId: [''],
      firstName: ['', [Validators.required, Validators.minLength(6)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: [''],
      phoneNumber: [
        '',
        [Validators.required, Validators.pattern('^[0-9]{10}$')],
      ],
      address: ['', [Validators.required]],
      role: [''],
    });

    this.loadAccountInfo();
  }

  loadAccountInfo() {
    this.accountService.accountInfo$.subscribe(
      (accountData: Account | null) => {
        if (accountData) {
          this.form.patchValue({
            userId: accountData.userId,
            firstName: accountData.firstName,
            lastName: accountData.lastName,
            email: accountData.email,
            phoneNumber: accountData.phoneNumber,
            address: accountData.address,
            role: accountData.role,
          });
        }
      }
    );
  }

  toggleEdit() {
    this.isEditMode = !this.isEditMode;
    if (this.isEditMode) {
      this.form.controls['firstName'].enable();
      this.form.controls['lastName'].enable();
      this.form.controls['phoneNumber'].enable();
      this.form.controls['address'].enable();
    } else {
      this.form.controls['firstName'].disable();
      this.form.controls['lastName'].disable();
      this.form.controls['phoneNumber'].disable();
      this.form.controls['address'].disable();
    }
  }

  onSubmit() {
    if (this.form.valid) {
      let formData = this.form.value;
      console.log(formData);
      this.accountService.updatePersonalInformation(formData).subscribe({
        next: (response) => {
          console.log('Update Success:', response);
          this.accountService.getPersonalInfo().subscribe((response) => {
            const accountData = response.body as Account;
            this.accountService.setAccountInfo(accountData);
            this.loadAccountInfo();
          });
          this.toggleEdit();
        },
      });
    } else {
      console.log('Form invalid');
    }
  }
}
