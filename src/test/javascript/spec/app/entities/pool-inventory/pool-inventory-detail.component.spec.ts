/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { PoolInventoryDetailComponent } from 'app/entities/pool-inventory/pool-inventory-detail.component';
import { PoolInventory } from 'app/shared/model/pool-inventory.model';

describe('Component Tests', () => {
  describe('PoolInventory Management Detail Component', () => {
    let comp: PoolInventoryDetailComponent;
    let fixture: ComponentFixture<PoolInventoryDetailComponent>;
    const route = ({ data: of({ poolInventory: new PoolInventory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [PoolInventoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PoolInventoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoolInventoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.poolInventory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
