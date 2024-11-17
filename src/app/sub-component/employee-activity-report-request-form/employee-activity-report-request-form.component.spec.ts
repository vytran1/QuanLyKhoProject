import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeActivityReportRequestFormComponent } from './employee-activity-report-request-form.component';

describe('EmployeeActivityReportRequestFormComponent', () => {
  let component: EmployeeActivityReportRequestFormComponent;
  let fixture: ComponentFixture<EmployeeActivityReportRequestFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeActivityReportRequestFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeeActivityReportRequestFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
