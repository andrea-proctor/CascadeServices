/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CascadeServicesTestModule } from '../../../test.module';
import { SupervisorsDeleteDialogComponent } from 'app/entities/supervisors/supervisors-delete-dialog.component';
import { SupervisorsService } from 'app/entities/supervisors/supervisors.service';

describe('Component Tests', () => {
  describe('Supervisors Management Delete Component', () => {
    let comp: SupervisorsDeleteDialogComponent;
    let fixture: ComponentFixture<SupervisorsDeleteDialogComponent>;
    let service: SupervisorsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [SupervisorsDeleteDialogComponent]
      })
        .overrideTemplate(SupervisorsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupervisorsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupervisorsService);
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
