import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TruckInventory } from 'app/shared/model/truck-inventory.model';
import { TruckInventoryService } from './truck-inventory.service';
import { TruckInventoryComponent } from './truck-inventory.component';
import { TruckInventoryDetailComponent } from './truck-inventory-detail.component';
import { TruckInventoryUpdateComponent } from './truck-inventory-update.component';
import { TruckInventoryDeletePopupComponent } from './truck-inventory-delete-dialog.component';
import { ITruckInventory } from 'app/shared/model/truck-inventory.model';

@Injectable({ providedIn: 'root' })
export class TruckInventoryResolve implements Resolve<ITruckInventory> {
  constructor(private service: TruckInventoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITruckInventory> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TruckInventory>) => response.ok),
        map((truckInventory: HttpResponse<TruckInventory>) => truckInventory.body)
      );
    }
    return of(new TruckInventory());
  }
}

export const truckInventoryRoute: Routes = [
  {
    path: '',
    component: TruckInventoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TruckInventories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TruckInventoryDetailComponent,
    resolve: {
      truckInventory: TruckInventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TruckInventories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TruckInventoryUpdateComponent,
    resolve: {
      truckInventory: TruckInventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TruckInventories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TruckInventoryUpdateComponent,
    resolve: {
      truckInventory: TruckInventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TruckInventories'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const truckInventoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TruckInventoryDeletePopupComponent,
    resolve: {
      truckInventory: TruckInventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TruckInventories'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
