/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CascadeServicesTestModule } from '../../../test.module';
import { InventoryValuesComponent } from 'app/entities/inventory-values/inventory-values.component';
import { InventoryValuesService } from 'app/entities/inventory-values/inventory-values.service';
import { InventoryValues } from 'app/shared/model/inventory-values.model';

describe('Component Tests', () => {
  describe('InventoryValues Management Component', () => {
    let comp: InventoryValuesComponent;
    let fixture: ComponentFixture<InventoryValuesComponent>;
    let service: InventoryValuesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [InventoryValuesComponent],
        providers: []
      })
        .overrideTemplate(InventoryValuesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryValuesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryValuesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InventoryValues(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.inventoryValues[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
