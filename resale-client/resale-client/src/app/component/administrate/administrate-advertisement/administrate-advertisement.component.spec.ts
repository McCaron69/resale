import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrateAdvertisementComponent } from './administrate-advertisement.component';

describe('AdministrateAdvertisementComponent', () => {
  let component: AdministrateAdvertisementComponent;
  let fixture: ComponentFixture<AdministrateAdvertisementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrateAdvertisementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministrateAdvertisementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
