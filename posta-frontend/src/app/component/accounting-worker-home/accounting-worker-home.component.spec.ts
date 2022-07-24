import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountingWorkerHomeComponent } from './accounting-worker-home.component';

describe('AccountingWorkerHomeComponent', () => {
  let component: AccountingWorkerHomeComponent;
  let fixture: ComponentFixture<AccountingWorkerHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountingWorkerHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountingWorkerHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
