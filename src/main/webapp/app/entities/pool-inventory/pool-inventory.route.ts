import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PoolInventory } from 'app/shared/model/pool-inventory.model';
import { PoolInventoryService } from './pool-inventory.service';
import { PoolInventoryComponent } from './pool-inventory.component';
import { PoolInventoryDetailComponent } from './pool-inventory-detail.component';
import { PoolInventoryUpdateComponent } from './pool-inventory-update.component';
import { PoolInventoryDeletePopupComponent } from './pool-inventory-delete-dialog.component';
import { IPoolInventory } from 'app/shared/model/pool-inventory.model';

@Injectable({ providedIn: 'root' })
export class PoolInventoryResolve implements Resolve<IPoolInventory> {
  constructor(private service: PoolInventoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPoolInventory> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PoolInventory>) => response.ok),
        map((poolInventory: HttpResponse<PoolInventory>) => poolInventory.body)
      );
    }
    return of(new PoolInventory());
  }
}

export const poolInventoryRoute: Routes = [
  {
    path: '',
    component: PoolInventoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PoolInventories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PoolInventoryDetailComponent,
    resolve: {
      poolInventory: PoolInventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PoolInventories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PoolInventoryUpdateComponent,
    resolve: {
      poolInventory: PoolInventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PoolInventories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PoolInventoryUpdateComponent,
    resolve: {
      poolInventory: PoolInventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PoolInventories'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const poolInventoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PoolInventoryDeletePopupComponent,
    resolve: {
      poolInventory: PoolInventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PoolInventories'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
