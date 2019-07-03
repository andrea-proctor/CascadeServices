/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { TruckInventoryDetailComponent } from 'app/entities/truck-inventory/truck-inventory-detail.component';
import { TruckInventory } from 'app/shared/model/truck-inventory.model';

describe('Component Tests', () => {
  describe('TruckInventory Management Detail Component', () => {
    let comp: TruckInventoryDetailComponent;
    let fixture: ComponentFixture<TruckInventoryDetailComponent>;
    const route = ({ data: of({ truckInventory: new TruckInventory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [TruckInventoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TruckInventoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TruckInventoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.truckInventory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
