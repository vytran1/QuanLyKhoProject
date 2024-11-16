import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryOrderDetailFormComponent } from './inventory-order-detail-form.component';

describe('InventoryOrderDetailFormComponent', () => {
  let component: InventoryOrderDetailFormComponent;
  let fixture: ComponentFixture<InventoryOrderDetailFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryOrderDetailFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryOrderDetailFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
