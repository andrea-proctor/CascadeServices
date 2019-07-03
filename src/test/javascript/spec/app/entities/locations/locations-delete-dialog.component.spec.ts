/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CascadeServicesTestModule } from '../../../test.module';
import { LocationsDeleteDialogComponent } from 'app/entities/locations/locations-delete-dialog.component';
import { LocationsService } from 'app/entities/locations/locations.service';

describe('Component Tests', () => {
  describe('Locations Management Delete Component', () => {
    let comp: LocationsDeleteDialogComponent;
    let fixture: ComponentFixture<LocationsDeleteDialogComponent>;
    let service: LocationsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [LocationsDeleteDialogComponent]
      })
        .overrideTemplate(LocationsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationsService);
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
