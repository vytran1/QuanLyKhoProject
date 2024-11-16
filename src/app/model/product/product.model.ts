import { Brand } from './brand.model';
import { Category } from './category.model';

export interface Product {
  id: number;
  alias: string;
  name: string;
  description: string;
  category: Category;
  brand: Brand;
}
