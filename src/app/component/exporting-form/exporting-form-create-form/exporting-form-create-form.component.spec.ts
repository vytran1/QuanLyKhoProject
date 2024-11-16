import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportingFormCreateFormComponent } from './exporting-form-create-form.component';

describe('ExportingFormCreateFormComponent', () => {
  let component: ExportingFormCreateFormComponent;
  let fixture: ComponentFixture<ExportingFormCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExportingFormCreateFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExportingFormCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
