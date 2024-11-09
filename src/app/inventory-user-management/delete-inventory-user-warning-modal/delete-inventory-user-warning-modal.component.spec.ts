import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteInventoryUserWarningModalComponent } from './delete-inventory-user-warning-modal.component';

describe('DeleteInventoryUserWarningModalComponent', () => {
  let component: DeleteInventoryUserWarningModalComponent;
  let fixture: ComponentFixture<DeleteInventoryUserWarningModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteInventoryUserWarningModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteInventoryUserWarningModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
