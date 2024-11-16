import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportingFormDetailComponent } from './exporting-form-detail.component';

describe('ExportingFormDetailComponent', () => {
  let component: ExportingFormDetailComponent;
  let fixture: ComponentFixture<ExportingFormDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExportingFormDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExportingFormDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
