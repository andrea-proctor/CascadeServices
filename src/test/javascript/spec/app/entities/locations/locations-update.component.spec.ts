/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { LocationsUpdateComponent } from 'app/entities/locations/locations-update.component';
import { LocationsService } from 'app/entities/locations/locations.service';
import { Locations } from 'app/shared/model/locations.model';

describe('Component Tests', () => {
  describe('Locations Management Update Component', () => {
    let comp: LocationsUpdateComponent;
    let fixture: ComponentFixture<LocationsUpdateComponent>;
    let service: LocationsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [LocationsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LocationsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocationsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Locations(123);
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
        const entity = new Locations();
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
