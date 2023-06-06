import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrateFeatureComponent } from './administrate-feature.component';

describe('AdministrateFeatureComponent', () => {
  let component: AdministrateFeatureComponent;
  let fixture: ComponentFixture<AdministrateFeatureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrateFeatureComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministrateFeatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
