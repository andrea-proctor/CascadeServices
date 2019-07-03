import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CascadeServicesSharedModule } from 'app/shared';
import {
  TruckInventoryComponent,
  TruckInventoryDetailComponent,
  TruckInventoryUpdateComponent,
  TruckInventoryDeletePopupComponent,
  TruckInventoryDeleteDialogComponent,
  truckInventoryRoute,
  truckInventoryPopupRoute
} from './';

const ENTITY_STATES = [...truckInventoryRoute, ...truckInventoryPopupRoute];

@NgModule({
  imports: [CascadeServicesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TruckInventoryComponent,
    TruckInventoryDetailComponent,
    TruckInventoryUpdateComponent,
    TruckInventoryDeleteDialogComponent,
    TruckInventoryDeletePopupComponent
  ],
  entryComponents: [
    TruckInventoryComponent,
    TruckInventoryUpdateComponent,
    TruckInventoryDeleteDialogComponent,
    TruckInventoryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesTruckInventoryModule {}
