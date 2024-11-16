import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportingFormDetailComponent } from './importing-form-detail.component';

describe('ImportingFormDetailComponent', () => {
  let component: ImportingFormDetailComponent;
  let fixture: ComponentFixture<ImportingFormDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImportingFormDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImportingFormDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
