import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddShipmentAccountingWorkerComponent } from './add-shipment-accounting-worker.component';

describe('AddShipmentAccountingWorkerComponent', () => {
  let component: AddShipmentAccountingWorkerComponent;
  let fixture: ComponentFixture<AddShipmentAccountingWorkerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddShipmentAccountingWorkerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddShipmentAccountingWorkerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
