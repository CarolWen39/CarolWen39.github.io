import { TestBed } from '@angular/core/testing';

import { NodebackendService } from './nodebackend.service';

describe('NodebackendService', () => {
  let service: NodebackendService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NodebackendService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
