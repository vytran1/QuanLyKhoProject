import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportingFormComponent } from './importing-form.component';

describe('ImportingFormComponent', () => {
  let component: ImportingFormComponent;
  let fixture: ComponentFixture<ImportingFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImportingFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImportingFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
