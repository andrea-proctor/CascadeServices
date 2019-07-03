/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CascadeServicesTestModule } from '../../../test.module';
import { ServiceOrdersDeleteDialogComponent } from 'app/entities/service-orders/service-orders-delete-dialog.component';
import { ServiceOrdersService } from 'app/entities/service-orders/service-orders.service';

describe('Component Tests', () => {
  describe('ServiceOrders Management Delete Component', () => {
    let comp: ServiceOrdersDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceOrdersDeleteDialogComponent>;
    let service: ServiceOrdersService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [ServiceOrdersDeleteDialogComponent]
      })
        .overrideTemplate(ServiceOrdersDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceOrdersDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceOrdersService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
