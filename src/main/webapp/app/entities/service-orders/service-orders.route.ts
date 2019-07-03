import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceOrders } from 'app/shared/model/service-orders.model';
import { ServiceOrdersService } from './service-orders.service';
import { ServiceOrdersComponent } from './service-orders.component';
import { ServiceOrdersDetailComponent } from './service-orders-detail.component';
import { ServiceOrdersUpdateComponent } from './service-orders-update.component';
import { ServiceOrdersDeletePopupComponent } from './service-orders-delete-dialog.component';
import { IServiceOrders } from 'app/shared/model/service-orders.model';

@Injectable({ providedIn: 'root' })
export class ServiceOrdersResolve implements Resolve<IServiceOrders> {
  constructor(private service: ServiceOrdersService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceOrders> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceOrders>) => response.ok),
        map((serviceOrders: HttpResponse<ServiceOrders>) => serviceOrders.body)
      );
    }
    return of(new ServiceOrders());
  }
}

export const serviceOrdersRoute: Routes = [
  {
    path: '',
    component: ServiceOrdersComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceOrdersDetailComponent,
    resolve: {
      serviceOrders: ServiceOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceOrdersUpdateComponent,
    resolve: {
      serviceOrders: ServiceOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceOrdersUpdateComponent,
    resolve: {
      serviceOrders: ServiceOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOrders'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceOrdersPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceOrdersDeletePopupComponent,
    resolve: {
      serviceOrders: ServiceOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOrders'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
