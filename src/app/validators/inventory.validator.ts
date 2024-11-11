import {
  AbstractControl,
  AsyncValidatorFn,
  ValidationErrors,
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
