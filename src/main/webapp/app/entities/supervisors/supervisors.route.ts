import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Supervisors } from 'app/shared/model/supervisors.model';
import { SupervisorsService } from './supervisors.service';
import { SupervisorsComponent } from './supervisors.component';
import { SupervisorsDetailComponent } from './supervisors-detail.component';
import { SupervisorsUpdateComponent } from './supervisors-update.component';
import { SupervisorsDeletePopupComponent } from './supervisors-delete-dialog.component';
import { ISupervisors } from 'app/shared/model/supervisors.model';

@Injectable({ providedIn: 'root' })
export class SupervisorsResolve implements Resolve<ISupervisors> {
  constructor(private service: SupervisorsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupervisors> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Supervisors>) => response.ok),
        map((supervisors: HttpResponse<Supervisors>) => supervisors.body)
      );
    }
    return of(new Supervisors());
  }
}

export const supervisorsRoute: Routes = [
  {
    path: '',
    component: SupervisorsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Supervisors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SupervisorsDetailComponent,
    resolve: {
      supervisors: SupervisorsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Supervisors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SupervisorsUpdateComponent,
    resolve: {
      supervisors: SupervisorsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Supervisors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SupervisorsUpdateComponent,
    resolve: {
      supervisors: SupervisorsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Supervisors'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const supervisorsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SupervisorsDeletePopupComponent,
    resolve: {
      supervisors: SupervisorsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Supervisors'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
