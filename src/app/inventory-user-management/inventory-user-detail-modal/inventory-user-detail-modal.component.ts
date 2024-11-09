import { Component, EventEmitter, Input, Output } from '@angular/core';
import { InventoryUser } from '../../model/inventory-user/inventory-user.model';

@Component({
  selector: 'app-inventory-user-detail-modal',
  standalone: true,
  imports: [],
  templateUrl: './inventory-user-detail-modal.component.html',
  styleUrl: './inventory-user-detail-modal.component.css',
})
export class InventoryUserDetailModalComponent {
  @Input()
  selectedInventoryUser: InventoryUser | null = null;

  @Output()
  close = new EventEmitter();

  closeModal() {
    this.close.emit();
  }
}
