import { TestBed } from '@angular/core/testing';

import { TaskApprovalService } from './form-approval.service';

describe('TaskApprovalService', () => {
  let service: TaskApprovalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaskApprovalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
