/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CascadeServicesTestModule } from '../../../test.module';
import { TruckInventoryDeleteDialogComponent } from 'app/entities/truck-inventory/truck-inventory-delete-dialog.component';
import { TruckInventoryService } from 'app/entities/truck-inventory/truck-inventory.service';

describe('Component Tests', () => {
  describe('TruckInventory Management Delete Component', () => {
    let comp: TruckInventoryDeleteDialogComponent;
    let fixture: ComponentFixture<TruckInventoryDeleteDialogComponent>;
    let service: TruckInventoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [TruckInventoryDeleteDialogComponent]
      })
        .overrideTemplate(TruckInventoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TruckInventoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TruckInventoryService);
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
