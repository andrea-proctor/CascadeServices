/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CascadeServicesTestModule } from '../../../test.module';
import { ServiceOrdersComponent } from 'app/entities/service-orders/service-orders.component';
import { ServiceOrdersService } from 'app/entities/service-orders/service-orders.service';
import { ServiceOrders } from 'app/shared/model/service-orders.model';

describe('Component Tests', () => {
  describe('ServiceOrders Management Component', () => {
    let comp: ServiceOrdersComponent;
    let fixture: ComponentFixture<ServiceOrdersComponent>;
    let service: ServiceOrdersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [ServiceOrdersComponent],
        providers: []
      })
        .overrideTemplate(ServiceOrdersComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceOrdersComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceOrdersService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ServiceOrders(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.serviceOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
