/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CascadeServicesTestModule } from '../../../test.module';
import { ItemsRemovedFromPoolComponent } from 'app/entities/items-removed-from-pool/items-removed-from-pool.component';
import { ItemsRemovedFromPoolService } from 'app/entities/items-removed-from-pool/items-removed-from-pool.service';
import { ItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';

describe('Component Tests', () => {
  describe('ItemsRemovedFromPool Management Component', () => {
    let comp: ItemsRemovedFromPoolComponent;
    let fixture: ComponentFixture<ItemsRemovedFromPoolComponent>;
    let service: ItemsRemovedFromPoolService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [ItemsRemovedFromPoolComponent],
        providers: []
      })
        .overrideTemplate(ItemsRemovedFromPoolComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemsRemovedFromPoolComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemsRemovedFromPoolService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ItemsRemovedFromPool(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.itemsRemovedFromPools[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
