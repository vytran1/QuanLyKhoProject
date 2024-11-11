import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoriesManagementComponent } from './inventories-management.component';

describe('InventoriesManagementComponent', () => {
  let component: InventoriesManagementComponent;
  let fixture: ComponentFixture<InventoriesManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoriesManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoriesManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
