import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryProviderComponent } from './inventory-provider.component';

describe('InventoryProviderComponent', () => {
  let component: InventoryProviderComponent;
  let fixture: ComponentFixture<InventoryProviderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryProviderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
