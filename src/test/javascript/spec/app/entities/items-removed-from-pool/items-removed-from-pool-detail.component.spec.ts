/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { ItemsRemovedFromPoolDetailComponent } from 'app/entities/items-removed-from-pool/items-removed-from-pool-detail.component';
import { ItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';

describe('Component Tests', () => {
  describe('ItemsRemovedFromPool Management Detail Component', () => {
    let comp: ItemsRemovedFromPoolDetailComponent;
    let fixture: ComponentFixture<ItemsRemovedFromPoolDetailComponent>;
    const route = ({ data: of({ itemsRemovedFromPool: new ItemsRemovedFromPool(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [ItemsRemovedFromPoolDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ItemsRemovedFromPoolDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemsRemovedFromPoolDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemsRemovedFromPool).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
