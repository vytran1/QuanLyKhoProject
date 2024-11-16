import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryOrderCreateFormComponent } from './inventory-order-create-form.component';

describe('InventoryOrderCreateFormComponent', () => {
  let component: InventoryOrderCreateFormComponent;
  let fixture: ComponentFixture<InventoryOrderCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryOrderCreateFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryOrderCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
