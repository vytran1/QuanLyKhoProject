import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadExcelFileComponent } from './upload-excel-file.component';

describe('UploadExcelFileComponent', () => {
  let component: UploadExcelFileComponent;
  let fixture: ComponentFixture<UploadExcelFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UploadExcelFileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadExcelFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
