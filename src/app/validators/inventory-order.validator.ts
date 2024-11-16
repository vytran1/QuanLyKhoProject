import {
  AbstractControl,
  AsyncValidatorFn,
  FormArray,
  ValidationErrors,
  ValidatorFn,
} from '@angular/forms';
import { Observable, of } from 'rxjs';
import {
  debounceTime,
  map,
  switchMap,
  catchError,
  first,
} from 'rxjs/operators';
import { InventoryOrderService } from '../service/inventory-order.service';

export function orderIdExistValidator(
  inventoryOrderService: InventoryOrderService
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    const orderId = control.value;

    // Nếu orderId trống, không cần kiểm tra tính duy nhất
    if (!orderId) {
      return of(null);
    }

    return inventoryOrderService.isExist(orderId).pipe(
      switchMap((isExist: boolean) => {
        return isExist ? of({ orderIdExist: true }) : of(null);
      }),
      catchError(() => of(null)), // Xử lý lỗi, trả về null nếu xảy ra lỗi
      first() // Kết thúc Observable sau khi nhận được giá trị đầu tiên
    );
  };
}

export function supplierValidator(
  control: AbstractControl
): ValidationErrors | null {
  if (control.value === '') {
    return { supplierRequired: true };
  }
  return null;
}

export function minFormArrayLength(min: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (control instanceof FormArray) {
      return control.length >= min ? null : { minFormArrayLength: true };
    }
    return null;
  };
}
