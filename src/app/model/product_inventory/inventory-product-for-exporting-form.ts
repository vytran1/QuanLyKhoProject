import { ProductQuantity } from './product-quantity.model';

export interface InventoryProductForEPF {
  inventory: string;
  results: ProductQuantity[];
}
