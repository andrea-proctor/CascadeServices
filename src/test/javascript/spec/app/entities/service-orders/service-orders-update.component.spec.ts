/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { ServiceOrdersUpdateComponent } from 'app/entities/service-orders/service-orders-update.component';
import { ServiceOrdersService } from 'app/entities/service-orders/service-orders.service';
import { ServiceOrders } from 'app/shared/model/service-orders.model';

describe('Component Tests', () => {
  describe('ServiceOrders Management Update Component', () => {
    let comp: ServiceOrdersUpdateComponent;
    let fixture: ComponentFixture<ServiceOrdersUpdateComponent>;
    let service: ServiceOrdersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [ServiceOrdersUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceOrdersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceOrdersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceOrdersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceOrders(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceOrders();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
