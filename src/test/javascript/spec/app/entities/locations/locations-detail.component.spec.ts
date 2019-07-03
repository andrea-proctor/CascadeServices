/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { LocationsDetailComponent } from 'app/entities/locations/locations-detail.component';
import { Locations } from 'app/shared/model/locations.model';

describe('Component Tests', () => {
  describe('Locations Management Detail Component', () => {
    let comp: LocationsDetailComponent;
    let fixture: ComponentFixture<LocationsDetailComponent>;
    const route = ({ data: of({ locations: new Locations(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [LocationsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocationsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.locations).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
