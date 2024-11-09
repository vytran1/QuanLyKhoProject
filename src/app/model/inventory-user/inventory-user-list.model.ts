import { InventoryUser } from './inventory-user.model';

export interface InventoryUserList {
  inventoryUsers: InventoryUser[];
  pageNum: number;
  pageSize: number;
  sortField: string;
  sortDir: string;
  reverseDir: string;
  totalItems: number;
  totalPages: number;
}
