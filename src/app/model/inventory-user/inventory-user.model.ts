import { InventoryRole } from './inventory-role.model';

export interface InventoryUser {
  userId: string;
  identityNumber: string;
  firstName: string;
  lastName: string;
  address?: string; // optional nếu có thể không có giá trị
  phoneNumber: string;
  photos?: string; // optional
  email?: string; // optional
  password?: string; // optional
  resetPasswordToken?: string; // optional
  inventoryRole?: InventoryRole; // optional
  enabled?: boolean; // optional
}
