export interface Inventory {
  inventoryId: string;
  inventoryName: string;
  inventoryAddress: string;
  countryId?: number;
  countryName?: string;
  stateId?: number;
  stateName?: string;
  districtId?: number;
  districtName?: string;
}
