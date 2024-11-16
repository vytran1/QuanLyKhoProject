import { ImportingFormDetailDTO } from './importing-form-detail-dto.model';

export interface ImportingFormDTO {
  importingFormId: string;
  createdTime?: Date;
  inventoryOrder: string;
  inventoryUser?: string;
  inventory: string;
  importingDetails?: ImportingFormDetailDTO[];
}
