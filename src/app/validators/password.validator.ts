import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function passwordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return null; // Don't validate empty values to allow required validator to handle them
    }

    const hasNumber = /\d/.test(value);
    const hasLetter = /[a-zA-Z]/.test(value);
    const validLength = value.length >= 6;

    const passwordValid = hasNumber && hasLetter && validLength;

    return !passwordValid ? { passwordStrength: true } : null;
  };
}

export function matchValidator(matchTo: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const matchControl = control?.parent?.get(matchTo);
    return matchControl && control.value !== matchControl.value
      ? { isMatching: true }
      : null;
  };
}
