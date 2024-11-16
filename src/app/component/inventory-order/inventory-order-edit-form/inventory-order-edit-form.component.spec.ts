import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryOrderEditFormComponent } from './inventory-order-edit-form.component';

describe('InventoryOrderEditFormComponent', () => {
  let component: InventoryOrderEditFormComponent;
  let fixture: ComponentFixture<InventoryOrderEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryOrderEditFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryOrderEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
