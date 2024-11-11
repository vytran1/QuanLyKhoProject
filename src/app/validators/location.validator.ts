import { AbstractControl, ValidationErrors } from '@angular/forms';

export function selectValidator(
  control: AbstractControl
): ValidationErrors | null {
  if (control.value === '') {
    return { defaultSelected: true };
  }
  return null;
}
