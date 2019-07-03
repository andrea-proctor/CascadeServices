/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CascadeServicesTestModule } from '../../../test.module';
import { LocationsComponent } from 'app/entities/locations/locations.component';
import { LocationsService } from 'app/entities/locations/locations.service';
import { Locations } from 'app/shared/model/locations.model';

describe('Component Tests', () => {
  describe('Locations Management Component', () => {
    let comp: LocationsComponent;
    let fixture: ComponentFixture<LocationsComponent>;
    let service: LocationsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [LocationsComponent],
        providers: []
      })
        .overrideTemplate(LocationsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocationsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Locations(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.locations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
