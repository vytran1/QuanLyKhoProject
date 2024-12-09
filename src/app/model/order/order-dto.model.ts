import { OrderDetailDTO } from './order-detail-dto.model';

export interface OrderDTO {
  orderId: string;
  createdTime?: Date;
  //supplier: string;
  customerName: string;
  customerPhoneNumber: string;
  createUser?: string;
  inventoryId: string;
  providerName: string;
  providerId: number;
  orderDetails: OrderDetailDTO[];
}
