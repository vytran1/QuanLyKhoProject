import {
  AbstractControl,
  AsyncValidatorFn,
  ValidationErrors,
  ValidatorFn,
} from '@angular/forms';
import { of, Observable } from 'rxjs';
import { switchMap, catchError, first } from 'rxjs/operators';
import { ImportingFormService } from '../service/importing-form.service'; // Adjust the path as needed

export function importingFormIdExistValidator(
  importingFormService: ImportingFormService
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    const importingFormId = control.value;

    // If the importingFormId is empty, we don't need to check its existence
    if (!importingFormId) {
      return of(null);
    }

    // Use the service to check if the importingFormId exists
    return importingFormService.isExist(importingFormId).pipe(
      switchMap((isExist: boolean) => {
        // If the form ID exists, return a validation error
        return isExist ? of({ importingFormIdExists: true }) : of(null);
      }),
      catchError(() => of(null)), // In case of an error, return null to allow the form to continue
      first() // Ensure only the first response is used
    );
  };
}

export function quantityValidator(orderedQuantity: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const quantity = control.value;
    return quantity && quantity > orderedQuantity
      ? { quantityExceeds: true }
      : null;
  };
}
