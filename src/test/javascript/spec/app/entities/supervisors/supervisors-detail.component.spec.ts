/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CascadeServicesTestModule } from '../../../test.module';
import { SupervisorsDetailComponent } from 'app/entities/supervisors/supervisors-detail.component';
import { Supervisors } from 'app/shared/model/supervisors.model';

describe('Component Tests', () => {
  describe('Supervisors Management Detail Component', () => {
    let comp: SupervisorsDetailComponent;
    let fixture: ComponentFixture<SupervisorsDetailComponent>;
    const route = ({ data: of({ supervisors: new Supervisors(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CascadeServicesTestModule],
        declarations: [SupervisorsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupervisorsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupervisorsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supervisors).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
