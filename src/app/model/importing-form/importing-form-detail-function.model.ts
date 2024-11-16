import { ImportingFormDetailDTO } from './importing-form-detail-dto.model';

export interface ImportingFormDTOForDetailFunction {
  importingFormId: string;
  createdTime: Date;
  inventoryUserId: string;
  inventoryUserFullname: string;
  orderId: string;
  customerName: string;
  inventoryId: string;
  inventoryAddress: string;
  totalAmount: number;
  details: ImportingFormDetailDTO[];
}
