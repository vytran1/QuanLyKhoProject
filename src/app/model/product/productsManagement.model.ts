export interface Product {
    id: number;
    name: string;
    alias: string;
    createdTime: string;
    updatedTime: string;
    unit?: string;
    enabled?: boolean;
    description?: string;
    price: number;
    categoryId?: number;
    brandId?: number;
  }
  