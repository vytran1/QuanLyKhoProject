import {
  AbstractControl,
  AsyncValidatorFn,
  ValidationErrors,
} from '@angular/forms';
import { map, catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { ExportingFormService } from '../service/exporting-form.service'; // Adjust the path as needed

export function exportingFormIdValidator(
  exportingFormService: ExportingFormService
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    // Check if the control value is valid (skip validation for empty controls)
    if (!control.value) {
      return of(null);
    }

    return exportingFormService.isExist(control.value).pipe(
      map((exists: boolean) =>
        exists ? { exportingFormIdExists: true } : null
      ), // Map result to validation error or null
      catchError(() => of(null)) // Handle errors gracefully
    );
  };
}
