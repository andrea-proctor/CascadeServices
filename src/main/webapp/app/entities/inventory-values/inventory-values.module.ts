import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CascadeServicesSharedModule } from 'app/shared';
import {
  InventoryValuesComponent,
  InventoryValuesDetailComponent,
  InventoryValuesUpdateComponent,
  InventoryValuesDeletePopupComponent,
  InventoryValuesDeleteDialogComponent,
  inventoryValuesRoute,
  inventoryValuesPopupRoute
} from './';

const ENTITY_STATES = [...inventoryValuesRoute, ...inventoryValuesPopupRoute];

@NgModule({
  imports: [CascadeServicesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InventoryValuesComponent,
    InventoryValuesDetailComponent,
    InventoryValuesUpdateComponent,
    InventoryValuesDeleteDialogComponent,
    InventoryValuesDeletePopupComponent
  ],
  entryComponents: [
    InventoryValuesComponent,
    InventoryValuesUpdateComponent,
    InventoryValuesDeleteDialogComponent,
    InventoryValuesDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesInventoryValuesModule {}
