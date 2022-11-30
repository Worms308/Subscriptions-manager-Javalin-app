import { TestBed } from '@angular/core/testing';

import { SubscriptionClientService } from './subscription-client.service';

describe('SubscriptionClientService', () => {
  let service: SubscriptionClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubscriptionClientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
