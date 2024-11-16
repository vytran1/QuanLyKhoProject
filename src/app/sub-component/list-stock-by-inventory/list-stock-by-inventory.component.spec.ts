import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStockByInventoryComponent } from './list-stock-by-inventory.component';

describe('ListStockByInventoryComponent', () => {
  let component: ListStockByInventoryComponent;
  let fixture: ComponentFixture<ListStockByInventoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListStockByInventoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListStockByInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
