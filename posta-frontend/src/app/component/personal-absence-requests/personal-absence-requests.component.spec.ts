import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalAbsenceRequestsComponent } from './personal-absence-requests.component';

describe('PersonalAbsenceRequestsComponent', () => {
  let component: PersonalAbsenceRequestsComponent;
  let fixture: ComponentFixture<PersonalAbsenceRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonalAbsenceRequestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalAbsenceRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
