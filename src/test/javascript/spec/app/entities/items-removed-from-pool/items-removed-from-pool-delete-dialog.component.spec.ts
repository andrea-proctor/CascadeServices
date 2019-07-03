/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CascadeServicesTestModule } from '../../../test.module';
import { ItemsRemovedFromPoolDeleteDialogComponent } from 'app/entities/items-removed-from-pool/items-removed-from-pool-delete-dialog.component';
import { ItemsRemovedFromPoolService } from 'app/entities/items-removed-from-pool/items-removed-from-pool.service';

describe('Component Tests', () => {
  describe('ItemsRemovedFromPool Management Delete Component', () => {
    let comp: ItemsRemovedFromPoolDeleteDialogComponent;
    let fixture: ComponentFixture<ItemsRemovedFromPoolDeleteDialogComponent>;
    let service: ItemsRemovedFromPoolService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [ItemsRemovedFromPoolDeleteDialogComponent]
      })
        .overrideTemplate(ItemsRemovedFromPoolDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemsRemovedFromPoolDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemsRemovedFromPoolService);
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
