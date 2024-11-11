import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Country } from '../../model/location/country.model';
import { Subscription } from 'rxjs';
import { LocationService } from '../../service/location.service';
import { State } from '../../model/location/state.model';
import { District } from '../../model/location/district.model';
import { selectValidator } from '../../validators/location.validator';
import { inventoryIdValidator } from '../../validators/inventory.validator';
import { InventoriesManagementService } from '../../service/inventories-management.service';
import { Inventory } from '../../model/inventories/inventories.model';
import { MessageModalComponent } from '../../message-modal/message-modal.component';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-inventory-create-form',
  standalone: true,
  imports: [ReactiveFormsModule, MessageModalComponent, RouterModule],
  templateUrl: './inventory-create-form.component.html',
  styleUrl: './inventory-create-form.component.css',
})
export class InventoryCreateFormComponent implements OnInit, OnDestroy {
  form: any;
  subscriptions: Subscription[] = [];
  countries: Country[] = [];
  states: State[] = [];
  districts: District[] = [];
  //Modal message
  showModal = false;
  type: 'success' | 'error' = 'success';
  message = '';

  constructor(
    private formBuilder: FormBuilder,
    private locationService: LocationService,
    private inventoryService: InventoriesManagementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      inventoryId: [
        '',
        {
          validators: [Validators.required, Validators.minLength(9)],
          asyncValidators: [inventoryIdValidator(this.inventoryService)],
          updateOn: 'blur',
        },
      ],
      inventoryName: [
        '',
        {
          validators: [Validators.required, Validators.minLength(9)],
        },
      ],
      inventoryAddress: [
        '',
        {
          validators: [Validators.required, Validators.minLength(9)],
        },
      ],
      countryId: ['', [Validators.required, selectValidator]],
      stateId: ['', [Validators.required, selectValidator]],
      districtId: ['', [Validators.required, selectValidator]],
    });

    this.subscriptions.push(
      this.locationService.getCountries().subscribe({
        next: (response) => {
          this.countries = response.body;
        },
        error: (error) => {
          console.log(error);
        },
      })
    );

    this.subscriptions.push(
      this.form.get('countryId').valueChanges.subscribe((countryId: any) => {
        if (countryId && countryId !== '') {
          this.subscriptions.push(
            this.locationService.getStates(countryId).subscribe({
              next: (response) => {
                this.states = response.body;
                this.form.get('stateId').enable();
                this.form.get('stateId').setValue(''); // Reset state selection
                this.form.get('districtId').disable(); // Disable district until state is chosen
                this.districts = []; // Clear previous districts
              },
            })
          );
        }
      })
    );

    this.subscriptions.push(
      this.form.get('stateId').valueChanges.subscribe((stateId: any) => {
        if (stateId && stateId !== '') {
          this.subscriptions.push(
            this.locationService.getDistricts(stateId).subscribe({
              next: (response) => {
                this.districts = response.body;
                this.form.get('districtId').enable();
                this.form.get('districtId').setValue(''); // Reset district selection
              },
            })
          );
        }
      })
    );

    this.form.get('stateId').disable();
    this.form.get('districtId').disable();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  addInventory() {
    if (this.form.valid) {
      let requestBody: Inventory = this.form.value;
      this.subscriptions.push(
        this.inventoryService.addInventory(requestBody).subscribe({
          next: (response) => {
            console.log('Add Inventory');
            console.log(response);
            this.message = 'Success Add Inventory';
            this.type = 'success';
            this.showModal = true;
          },
          error: (error) => {
            console.log('Has Error When Create Inventory');
            console.log(error);
            this.message = error.error;
            this.type = 'error';
            this.showModal = true;
          },
        })
      );
    } else {
      console.log('Invalid');
    }
  }

  closeModalMessage() {
    if (this.type === 'success') {
      this.router.navigate(['/inventory/inventories']);
    } else {
      this.message = '';
      this.type = 'success';
      this.showModal = false;
    }
  }
}
