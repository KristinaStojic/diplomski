import { TestBed } from '@angular/core/testing';

import { PayoffService } from './payoff.service';

describe('PayoffService', () => {
  let service: PayoffService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PayoffService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
