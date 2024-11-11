import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Inventory } from '../../model/inventories/inventories.model';
import { BehaviorSubject } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inventories-modal-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventories-modal-details.component.html',
  styleUrl: './inventories-modal-details.component.css',
})
export class InventoriesModalDetailsComponent {
  @Input()
  showModal: boolean = false;

  @Input()
  inventory: Inventory | null = null;

  @Output()
  closeModalEvent = new EventEmitter<void>();

  closeModal() {
    this.closeModalEvent.emit();
  }
}
