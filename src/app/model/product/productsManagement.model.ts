import { Brand } from "./brand.model";
import { Category } from "./category.model";

export interface Product {
    id: number;
    name: string;
    alias: string;
    unit?: string;
    enabled?: boolean;
    description?: string;
    price?: number;
    categoryId?: number;
    categoryName: string;
    brandId?: number;
    brandName?: string;
  }
  