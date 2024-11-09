import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-delete-inventory-user-warning-modal',
  standalone: true,
  imports: [],
  templateUrl: './delete-inventory-user-warning-modal.component.html',
  styleUrl: './delete-inventory-user-warning-modal.component.css',
})
export class DeleteInventoryUserWarningModalComponent {
  @Input()
  isVisible = true;

  @Input()
  message = '';

  @Output()
  confirmed = new EventEmitter();

  @Output()
  canceled = new EventEmitter();

  confirmDeletion() {
    this.confirmed.emit();
    this.isVisible = false;
  }

  cancel() {
    this.canceled.emit();
    this.isVisible = false;
  }
}
