import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryUserEditFormComponent } from './inventory-user-edit-form.component';

describe('InventoryUserEditFormComponent', () => {
  let component: InventoryUserEditFormComponent;
  let fixture: ComponentFixture<InventoryUserEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryUserEditFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryUserEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
