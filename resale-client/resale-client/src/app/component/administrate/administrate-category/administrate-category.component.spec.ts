import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrateCategoryComponent } from './administrate-category.component';

describe('AdministrateCategoryComponent', () => {
  let component: AdministrateCategoryComponent;
  let fixture: ComponentFixture<AdministrateCategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrateCategoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministrateCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
