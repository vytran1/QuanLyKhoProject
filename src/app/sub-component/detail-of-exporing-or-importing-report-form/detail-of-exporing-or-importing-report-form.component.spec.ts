import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailOfExporingOrImportingReportFormComponent } from './detail-of-exporing-or-importing-report-form.component';

describe('DetailOfExporingOrImportingReportFormComponent', () => {
  let component: DetailOfExporingOrImportingReportFormComponent;
  let fixture: ComponentFixture<DetailOfExporingOrImportingReportFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailOfExporingOrImportingReportFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailOfExporingOrImportingReportFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
