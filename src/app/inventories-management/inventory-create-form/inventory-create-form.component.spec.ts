import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryCreateFormComponent } from './inventory-create-form.component';

describe('InventoryCreateFormComponent', () => {
  let component: InventoryCreateFormComponent;
  let fixture: ComponentFixture<InventoryCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryCreateFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
