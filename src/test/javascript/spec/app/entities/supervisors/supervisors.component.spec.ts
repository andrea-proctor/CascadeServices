/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CascadeServicesTestModule } from '../../../test.module';
import { SupervisorsComponent } from 'app/entities/supervisors/supervisors.component';
import { SupervisorsService } from 'app/entities/supervisors/supervisors.service';
import { Supervisors } from 'app/shared/model/supervisors.model';

describe('Component Tests', () => {
  describe('Supervisors Management Component', () => {
    let comp: SupervisorsComponent;
    let fixture: ComponentFixture<SupervisorsComponent>;
    let service: SupervisorsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [SupervisorsComponent],
        providers: []
      })
        .overrideTemplate(SupervisorsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupervisorsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupervisorsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Supervisors(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.supervisors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
