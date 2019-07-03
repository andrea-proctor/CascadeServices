/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { InventoryValuesDetailComponent } from 'app/entities/inventory-values/inventory-values-detail.component';
import { InventoryValues } from 'app/shared/model/inventory-values.model';

describe('Component Tests', () => {
  describe('InventoryValues Management Detail Component', () => {
    let comp: InventoryValuesDetailComponent;
    let fixture: ComponentFixture<InventoryValuesDetailComponent>;
    const route = ({ data: of({ inventoryValues: new InventoryValues(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [InventoryValuesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InventoryValuesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryValuesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryValues).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
