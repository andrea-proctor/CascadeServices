/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { PoolInventoryUpdateComponent } from 'app/entities/pool-inventory/pool-inventory-update.component';
import { PoolInventoryService } from 'app/entities/pool-inventory/pool-inventory.service';
import { PoolInventory } from 'app/shared/model/pool-inventory.model';

describe('Component Tests', () => {
  describe('PoolInventory Management Update Component', () => {
    let comp: PoolInventoryUpdateComponent;
    let fixture: ComponentFixture<PoolInventoryUpdateComponent>;
    let service: PoolInventoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [PoolInventoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PoolInventoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoolInventoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoolInventoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PoolInventory(123);
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
        const entity = new PoolInventory();
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
