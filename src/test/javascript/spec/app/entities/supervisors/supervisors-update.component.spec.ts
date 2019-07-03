/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { SupervisorsUpdateComponent } from 'app/entities/supervisors/supervisors-update.component';
import { SupervisorsService } from 'app/entities/supervisors/supervisors.service';
import { Supervisors } from 'app/shared/model/supervisors.model';

describe('Component Tests', () => {
  describe('Supervisors Management Update Component', () => {
    let comp: SupervisorsUpdateComponent;
    let fixture: ComponentFixture<SupervisorsUpdateComponent>;
    let service: SupervisorsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [SupervisorsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SupervisorsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupervisorsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupervisorsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Supervisors(123);
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
        const entity = new Supervisors();
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
