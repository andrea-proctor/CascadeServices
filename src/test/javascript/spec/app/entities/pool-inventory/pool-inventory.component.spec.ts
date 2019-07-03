/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CascadeServicesTestModule } from '../../../test.module';
import { PoolInventoryComponent } from 'app/entities/pool-inventory/pool-inventory.component';
import { PoolInventoryService } from 'app/entities/pool-inventory/pool-inventory.service';
import { PoolInventory } from 'app/shared/model/pool-inventory.model';

describe('Component Tests', () => {
  describe('PoolInventory Management Component', () => {
    let comp: PoolInventoryComponent;
    let fixture: ComponentFixture<PoolInventoryComponent>;
    let service: PoolInventoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [PoolInventoryComponent],
        providers: []
      })
        .overrideTemplate(PoolInventoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoolInventoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoolInventoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PoolInventory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.poolInventories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
