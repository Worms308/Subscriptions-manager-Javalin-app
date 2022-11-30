import { TestBed } from '@angular/core/testing';

import { ChangeObserverService } from './change-observer.service';

describe('ChangeObserverService', () => {
  let service: ChangeObserverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChangeObserverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
