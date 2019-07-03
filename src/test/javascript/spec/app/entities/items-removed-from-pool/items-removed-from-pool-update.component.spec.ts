/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { ItemsRemovedFromPoolUpdateComponent } from 'app/entities/items-removed-from-pool/items-removed-from-pool-update.component';
import { ItemsRemovedFromPoolService } from 'app/entities/items-removed-from-pool/items-removed-from-pool.service';
import { ItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';

describe('Component Tests', () => {
  describe('ItemsRemovedFromPool Management Update Component', () => {
    let comp: ItemsRemovedFromPoolUpdateComponent;
    let fixture: ComponentFixture<ItemsRemovedFromPoolUpdateComponent>;
    let service: ItemsRemovedFromPoolService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [ItemsRemovedFromPoolUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ItemsRemovedFromPoolUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemsRemovedFromPoolUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemsRemovedFromPoolService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemsRemovedFromPool(123);
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
        const entity = new ItemsRemovedFromPool();
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
