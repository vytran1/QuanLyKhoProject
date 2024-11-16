import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderWithoutImportingformListComponent } from './order-without-importingform-list.component';

describe('OrderWithoutImportingformListComponent', () => {
  let component: OrderWithoutImportingformListComponent;
  let fixture: ComponentFixture<OrderWithoutImportingformListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderWithoutImportingformListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderWithoutImportingformListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
