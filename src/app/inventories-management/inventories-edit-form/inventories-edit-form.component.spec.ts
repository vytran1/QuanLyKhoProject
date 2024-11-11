import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoriesEditFormComponent } from './inventories-edit-form.component';

describe('InventoriesEditFormComponent', () => {
  let component: InventoriesEditFormComponent;
  let fixture: ComponentFixture<InventoriesEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoriesEditFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoriesEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
