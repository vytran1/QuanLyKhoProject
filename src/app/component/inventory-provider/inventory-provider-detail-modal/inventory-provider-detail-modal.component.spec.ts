import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryProviderDetailModalComponent } from './inventory-provider-detail-modal.component';

describe('InventoryProviderDetailModalComponent', () => {
  let component: InventoryProviderDetailModalComponent;
  let fixture: ComponentFixture<InventoryProviderDetailModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryProviderDetailModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryProviderDetailModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
