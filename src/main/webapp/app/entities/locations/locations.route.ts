import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Locations } from 'app/shared/model/locations.model';
import { LocationsService } from './locations.service';
import { LocationsComponent } from './locations.component';
import { LocationsDetailComponent } from './locations-detail.component';
import { LocationsUpdateComponent } from './locations-update.component';
import { LocationsDeletePopupComponent } from './locations-delete-dialog.component';
import { ILocations } from 'app/shared/model/locations.model';

@Injectable({ providedIn: 'root' })
export class LocationsResolve implements Resolve<ILocations> {
  constructor(private service: LocationsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILocations> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Locations>) => response.ok),
        map((locations: HttpResponse<Locations>) => locations.body)
      );
    }
    return of(new Locations());
  }
}

export const locationsRoute: Routes = [
  {
    path: '',
    component: LocationsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocationsDetailComponent,
    resolve: {
      locations: LocationsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocationsUpdateComponent,
    resolve: {
      locations: LocationsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocationsUpdateComponent,
    resolve: {
      locations: LocationsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const locationsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LocationsDeletePopupComponent,
    resolve: {
      locations: LocationsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
