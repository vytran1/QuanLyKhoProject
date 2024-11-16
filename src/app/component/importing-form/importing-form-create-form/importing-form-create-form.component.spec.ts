import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportingFormCreateFormComponent } from './importing-form-create-form.component';

describe('ImportingFormCreateFormComponent', () => {
  let component: ImportingFormCreateFormComponent;
  let fixture: ComponentFixture<ImportingFormCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImportingFormCreateFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImportingFormCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
