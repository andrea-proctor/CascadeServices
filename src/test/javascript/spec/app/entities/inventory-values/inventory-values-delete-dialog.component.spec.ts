/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CascadeServicesTestModule } from '../../../test.module';
import { InventoryValuesDeleteDialogComponent } from 'app/entities/inventory-values/inventory-values-delete-dialog.component';
import { InventoryValuesService } from 'app/entities/inventory-values/inventory-values.service';

describe('Component Tests', () => {
  describe('InventoryValues Management Delete Component', () => {
    let comp: InventoryValuesDeleteDialogComponent;
    let fixture: ComponentFixture<InventoryValuesDeleteDialogComponent>;
    let service: InventoryValuesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [InventoryValuesDeleteDialogComponent]
      })
        .overrideTemplate(InventoryValuesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryValuesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryValuesService);
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
