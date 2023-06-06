import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrateAddressComponent } from './administrate-address.component';

describe('AdministrateAddressComponent', () => {
  let component: AdministrateAddressComponent;
  let fixture: ComponentFixture<AdministrateAddressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrateAddressComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministrateAddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
