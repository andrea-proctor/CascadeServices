/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { TruckInventoryUpdateComponent } from 'app/entities/truck-inventory/truck-inventory-update.component';
import { TruckInventoryService } from 'app/entities/truck-inventory/truck-inventory.service';
import { TruckInventory } from 'app/shared/model/truck-inventory.model';

describe('Component Tests', () => {
  describe('TruckInventory Management Update Component', () => {
    let comp: TruckInventoryUpdateComponent;
    let fixture: ComponentFixture<TruckInventoryUpdateComponent>;
    let service: TruckInventoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [TruckInventoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TruckInventoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TruckInventoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TruckInventoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TruckInventory(123);
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
        const entity = new TruckInventory();
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
