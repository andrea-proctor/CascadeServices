import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CascadeServicesSharedModule } from 'app/shared';
import {
  PurchaseOrdersComponent,
  PurchaseOrdersDetailComponent,
  PurchaseOrdersUpdateComponent,
  PurchaseOrdersDeletePopupComponent,
  PurchaseOrdersDeleteDialogComponent,
  purchaseOrdersRoute,
  purchaseOrdersPopupRoute
} from './';

const ENTITY_STATES = [...purchaseOrdersRoute, ...purchaseOrdersPopupRoute];

@NgModule({
  imports: [CascadeServicesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PurchaseOrdersComponent,
    PurchaseOrdersDetailComponent,
    PurchaseOrdersUpdateComponent,
    PurchaseOrdersDeleteDialogComponent,
    PurchaseOrdersDeletePopupComponent
  ],
  entryComponents: [
    PurchaseOrdersComponent,
    PurchaseOrdersUpdateComponent,
    PurchaseOrdersDeleteDialogComponent,
    PurchaseOrdersDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesPurchaseOrdersModule {}
