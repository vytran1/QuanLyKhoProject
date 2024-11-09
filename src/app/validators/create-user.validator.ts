import {
  AbstractControl,
  AsyncValidatorFn,
  ValidationErrors,
} from '@angular/forms';
import { InventoryUserService } from '../service/inventory-user.service';
import {
  debounceTime,
  first,
  map,
  Observable,
  of,
  switchMap,
  switchMapTo,
} from 'rxjs';
import { InventoryUserManagementService } from '../service/inventory-user-management.service';

export function userIdDuplicate(
  inventoryUserService: InventoryUserManagementService
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    return inventoryUserService.checkUserIdDuplicate(control.value).pipe(
      map((isDuplicate) => (isDuplicate ? { userIdExists: true } : null)),
      first()
    );
  };
}

export function emailDuplicate(
  inventoryUserService: InventoryUserManagementService
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    return inventoryUserService.checkEmailDuplicate(control.value).pipe(
      map((isDuplicate) => (isDuplicate ? { emailExists: true } : null)),
      first()
    );
  };
}

export function identityNumberDuplicate(
  inventoryUserService: InventoryUserManagementService
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    return inventoryUserService
      .checkIdentityNumberDuplicate(control.value)
      .pipe(
        map((isDuplicate) =>
          isDuplicate ? { identityNumberExists: true } : null
        ),
        first()
      );
  };
}
