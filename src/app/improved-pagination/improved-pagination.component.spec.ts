import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImprovedPaginationComponent } from './improved-pagination.component';

describe('ImprovedPaginationComponent', () => {
  let component: ImprovedPaginationComponent;
  let fixture: ComponentFixture<ImprovedPaginationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImprovedPaginationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImprovedPaginationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
