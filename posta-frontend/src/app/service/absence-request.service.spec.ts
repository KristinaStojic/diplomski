import { TestBed } from '@angular/core/testing';

import { AbsenceRequestService } from './absence-request.service';

describe('AbsenceRequestService', () => {
  let service: AbsenceRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AbsenceRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
