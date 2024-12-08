import { Component, EventEmitter, Input, Output } from '@angular/core';
import { InventoryProvider } from '../../../model/provider/inventory_provider.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inventory-provider-detail-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventory-provider-detail-modal.component.html',
  styleUrl: './inventory-provider-detail-modal.component.css',
})
export class InventoryProviderDetailModalComponent {
  @Input()
  inventoryProvider: InventoryProvider | null = null;

  @Output()
  closeEmitEvent = new EventEmitter();

  closeModal() {
    this.closeEmitEvent.emit();
  }
}
