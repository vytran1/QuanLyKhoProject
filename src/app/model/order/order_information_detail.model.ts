import { OrderDetailDTO } from './order-detail-dto.model';

export interface OrderInformationDetailDTO {
  orderId: string;
  createdTime: Date;
  inventoryUserId: string;
  inventoryUserFullname: string;
  customerName: string;
  customerPhoneNumber: string;
  inventoryId: string;
  inventoryAddress: string;
  total: number;
  details: OrderDetailDTO[];
}
