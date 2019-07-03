/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CascadeServicesTestModule } from '../../../test.module';
import { TruckInventoryComponent } from 'app/entities/truck-inventory/truck-inventory.component';
import { TruckInventoryService } from 'app/entities/truck-inventory/truck-inventory.service';
import { TruckInventory } from 'app/shared/model/truck-inventory.model';

describe('Component Tests', () => {
  describe('TruckInventory Management Component', () => {
    let comp: TruckInventoryComponent;
    let fixture: ComponentFixture<TruckInventoryComponent>;
    let service: TruckInventoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [TruckInventoryComponent],
        providers: []
      })
        .overrideTemplate(TruckInventoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TruckInventoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TruckInventoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TruckInventory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.truckInventories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
