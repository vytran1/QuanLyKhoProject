export class InventoryQuantity {
  inventoryName: string;
  quantity: number;

  constructor(inventoryName: string, quantity: number) {
    this.inventoryName = inventoryName;
    this.quantity = quantity;
  }
}
