import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryProviderEditFormComponent } from './inventory-provider-edit-form.component';

describe('InventoryProviderEditFormComponent', () => {
  let component: InventoryProviderEditFormComponent;
  let fixture: ComponentFixture<InventoryProviderEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryProviderEditFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryProviderEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
