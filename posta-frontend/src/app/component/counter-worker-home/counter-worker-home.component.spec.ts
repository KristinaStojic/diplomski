import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CounterWorkerHomeComponent } from './counter-worker-home.component';

describe('CounterWorkerHomeComponent', () => {
  let component: CounterWorkerHomeComponent;
  let fixture: ComponentFixture<CounterWorkerHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CounterWorkerHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CounterWorkerHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
