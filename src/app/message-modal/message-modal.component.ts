import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-message-modal',
  standalone: true,
  imports: [],
  templateUrl: './message-modal.component.html',
  styleUrl: './message-modal.component.css',
})
export class MessageModalComponent {
  @Input()
  message: string | null = '';

  @Input()
  type: 'success' | 'error' = 'success';

  @Output()
  close = new EventEmitter();

  closeModal() {
    this.close.emit();
  }
}
