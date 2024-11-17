import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SummaryImpAndExpReportFormComponent } from './summary-imp-and-exp-report-form.component';

describe('SummaryImpAndExpReportFormComponent', () => {
  let component: SummaryImpAndExpReportFormComponent;
  let fixture: ComponentFixture<SummaryImpAndExpReportFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SummaryImpAndExpReportFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SummaryImpAndExpReportFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
