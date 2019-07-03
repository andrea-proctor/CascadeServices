/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { ServiceOrdersDetailComponent } from 'app/entities/service-orders/service-orders-detail.component';
import { ServiceOrders } from 'app/shared/model/service-orders.model';

describe('Component Tests', () => {
  describe('ServiceOrders Management Detail Component', () => {
    let comp: ServiceOrdersDetailComponent;
    let fixture: ComponentFixture<ServiceOrdersDetailComponent>;
    const route = ({ data: of({ serviceOrders: new ServiceOrders(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [ServiceOrdersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceOrdersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceOrdersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceOrders).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
