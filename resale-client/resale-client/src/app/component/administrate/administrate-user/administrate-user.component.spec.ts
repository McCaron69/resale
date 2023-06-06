import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrateUserComponent } from './administrate-user.component';

describe('AdministrateUserComponent', () => {
  let component: AdministrateUserComponent;
  let fixture: ComponentFixture<AdministrateUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrateUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministrateUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
