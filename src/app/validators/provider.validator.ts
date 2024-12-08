import {
  AbstractControl,
  AsyncValidatorFn,
  ValidationErrors,
} from '@angular/forms';
import { InventoryProviderService } from '../service/inventory-provider.service';
import { catchError, first, Observable, of, switchMap } from 'rxjs';

export function emailUniqueValidator(
  inventoryProviderService: InventoryProviderService,
  providerId: number | null // Thêm providerId vào tham số
): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    const email = control.value;

    // Nếu email trống, không cần kiểm tra tính duy nhất
    if (!email) {
      return of(null);
    }

    // Gọi API kiểm tra email duy nhất
    return inventoryProviderService.isUnique(providerId, email).pipe(
      switchMap((isUnique: boolean) => {
        // Trả về lỗi nếu email đã tồn tại
        return isUnique ? of(null) : of({ emailTaken: true });
      }),
      catchError(() => of(null)), // Xử lý lỗi, trả về null nếu có lỗi xảy ra
      first() // Kết thúc Observable sau khi nhận được giá trị đầu tiên
    );
  };
}
