import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InventoryValues } from 'app/shared/model/inventory-values.model';
import { InventoryValuesService } from './inventory-values.service';
import { InventoryValuesComponent } from './inventory-values.component';
import { InventoryValuesDetailComponent } from './inventory-values-detail.component';
import { InventoryValuesUpdateComponent } from './inventory-values-update.component';
import { InventoryValuesDeletePopupComponent } from './inventory-values-delete-dialog.component';
import { IInventoryValues } from 'app/shared/model/inventory-values.model';

@Injectable({ providedIn: 'root' })
export class InventoryValuesResolve implements Resolve<IInventoryValues> {
  constructor(private service: InventoryValuesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInventoryValues> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InventoryValues>) => response.ok),
        map((inventoryValues: HttpResponse<InventoryValues>) => inventoryValues.body)
      );
    }
    return of(new InventoryValues());
  }
}

export const inventoryValuesRoute: Routes = [
  {
    path: '',
    component: InventoryValuesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InventoryValues'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InventoryValuesDetailComponent,
    resolve: {
      inventoryValues: InventoryValuesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InventoryValues'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InventoryValuesUpdateComponent,
    resolve: {
      inventoryValues: InventoryValuesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InventoryValues'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InventoryValuesUpdateComponent,
    resolve: {
      inventoryValues: InventoryValuesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InventoryValues'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inventoryValuesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InventoryValuesDeletePopupComponent,
    resolve: {
      inventoryValues: InventoryValuesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InventoryValues'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
