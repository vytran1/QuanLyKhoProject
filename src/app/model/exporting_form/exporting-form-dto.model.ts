import { ExportingFormDetailDTO } from './exporting-form-detail-dto.model';

export interface ExportingFormDTO {
  exportingFormId: string;
  createdTime: Date;
  customerName: string;
  customerPhoneNumber: string;
  inventoryUser: string;
  inventory: string;
  exportingDetails: ExportingFormDetailDTO[];
}
