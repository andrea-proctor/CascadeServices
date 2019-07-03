/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { InventoryValuesUpdateComponent } from 'app/entities/inventory-values/inventory-values-update.component';
import { InventoryValuesService } from 'app/entities/inventory-values/inventory-values.service';
import { InventoryValues } from 'app/shared/model/inventory-values.model';

describe('Component Tests', () => {
  describe('InventoryValues Management Update Component', () => {
    let comp: InventoryValuesUpdateComponent;
    let fixture: ComponentFixture<InventoryValuesUpdateComponent>;
    let service: InventoryValuesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [InventoryValuesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InventoryValuesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryValuesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryValuesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryValues(123);
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
        const entity = new InventoryValues();
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
