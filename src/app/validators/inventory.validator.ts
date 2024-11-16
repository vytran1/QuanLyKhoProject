import {
  AbstractControl,
  AsyncValidatorFn,
  ValidationErrors,
  ValidatorFn,
} from '@angular/forms';
import { InventoriesManagementService } from '../service/inventories-management.service';
import { first, map, Observable, of } from 'rxjs';

export function inventoryIdValidator(
  inventoryService: InventoriesManagementService
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    if (!control.value) {
      return of(null);
    }
    return inventoryService.isExist(control.value).pipe(
      map((isDuplicate) => (isDuplicate ? { inventoryExist: true } : null)),
      first()
    );
  };
}

// Custom validator for date range
export const dateRangeValidator: ValidatorFn = (
  control: AbstractControl
): ValidationErrors | null => {
  const startDate = control.get('startDate')?.value;
  const endDate = control.get('endDate')?.value;

  // If both dates are provided and endDate is before startDate, set an error
  return startDate && endDate && endDate < startDate
    ? { invalidDateRange: true }
    : null;
};
