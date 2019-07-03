import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';
import { ItemsRemovedFromPoolService } from './items-removed-from-pool.service';
import { ItemsRemovedFromPoolComponent } from './items-removed-from-pool.component';
import { ItemsRemovedFromPoolDetailComponent } from './items-removed-from-pool-detail.component';
import { ItemsRemovedFromPoolUpdateComponent } from './items-removed-from-pool-update.component';
import { ItemsRemovedFromPoolDeletePopupComponent } from './items-removed-from-pool-delete-dialog.component';
import { IItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';

@Injectable({ providedIn: 'root' })
export class ItemsRemovedFromPoolResolve implements Resolve<IItemsRemovedFromPool> {
  constructor(private service: ItemsRemovedFromPoolService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IItemsRemovedFromPool> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ItemsRemovedFromPool>) => response.ok),
        map((itemsRemovedFromPool: HttpResponse<ItemsRemovedFromPool>) => itemsRemovedFromPool.body)
      );
    }
    return of(new ItemsRemovedFromPool());
  }
}

export const itemsRemovedFromPoolRoute: Routes = [
  {
    path: '',
    component: ItemsRemovedFromPoolComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ItemsRemovedFromPools'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ItemsRemovedFromPoolDetailComponent,
    resolve: {
      itemsRemovedFromPool: ItemsRemovedFromPoolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ItemsRemovedFromPools'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ItemsRemovedFromPoolUpdateComponent,
    resolve: {
      itemsRemovedFromPool: ItemsRemovedFromPoolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ItemsRemovedFromPools'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ItemsRemovedFromPoolUpdateComponent,
    resolve: {
      itemsRemovedFromPool: ItemsRemovedFromPoolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ItemsRemovedFromPools'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const itemsRemovedFromPoolPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ItemsRemovedFromPoolDeletePopupComponent,
    resolve: {
      itemsRemovedFromPool: ItemsRemovedFromPoolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ItemsRemovedFromPools'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
