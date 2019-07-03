import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CascadeServicesSharedModule } from 'app/shared';
import {
  PoolInventoryComponent,
  PoolInventoryDetailComponent,
  PoolInventoryUpdateComponent,
  PoolInventoryDeletePopupComponent,
  PoolInventoryDeleteDialogComponent,
  poolInventoryRoute,
  poolInventoryPopupRoute
} from './';

const ENTITY_STATES = [...poolInventoryRoute, ...poolInventoryPopupRoute];

@NgModule({
  imports: [CascadeServicesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PoolInventoryComponent,
    PoolInventoryDetailComponent,
    PoolInventoryUpdateComponent,
    PoolInventoryDeleteDialogComponent,
    PoolInventoryDeletePopupComponent
  ],
  entryComponents: [
    PoolInventoryComponent,
    PoolInventoryUpdateComponent,
    PoolInventoryDeleteDialogComponent,
    PoolInventoryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesPoolInventoryModule {}
