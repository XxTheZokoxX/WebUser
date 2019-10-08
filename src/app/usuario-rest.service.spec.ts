import { TestBed } from '@angular/core/testing';

import { UsuarioRestService } from './usuario-rest.service';

describe('UsuarioRestService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UsuarioRestService = TestBed.get(UsuarioRestService);
    expect(service).toBeTruthy();
  });
});
