import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryUserCreateFormComponent } from './inventory-user-create-form.component';

describe('InventoryUserCreateFormComponent', () => {
  let component: InventoryUserCreateFormComponent;
  let fixture: ComponentFixture<InventoryUserCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryUserCreateFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryUserCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
