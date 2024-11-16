import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportingFormComponent } from './exporting-form.component';

describe('ExportingFormComponent', () => {
  let component: ExportingFormComponent;
  let fixture: ComponentFixture<ExportingFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExportingFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExportingFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
