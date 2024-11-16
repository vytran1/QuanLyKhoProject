import { ExportingFormDetailDTO } from './exporting-form-detail-dto.model';

export interface ExportingFormDetails {
  exportingFormId: string;
  createdTime: Date;
  inventoryUserId: string;
  inventoryUserName: string;
  customerName: string;
  customerPhoneNumber: string;
  inventoryId: string;
  inventoryAddress: string;
  total: number;
  exportingDetails: ExportingFormDetailDTO[];
}
