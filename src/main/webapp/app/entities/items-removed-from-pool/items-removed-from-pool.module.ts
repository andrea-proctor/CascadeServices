import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CascadeServicesSharedModule } from 'app/shared';
import {
  ItemsRemovedFromPoolComponent,
  ItemsRemovedFromPoolDetailComponent,
  ItemsRemovedFromPoolUpdateComponent,
  ItemsRemovedFromPoolDeletePopupComponent,
  ItemsRemovedFromPoolDeleteDialogComponent,
  itemsRemovedFromPoolRoute,
  itemsRemovedFromPoolPopupRoute
} from './';

const ENTITY_STATES = [...itemsRemovedFromPoolRoute, ...itemsRemovedFromPoolPopupRoute];

@NgModule({
  imports: [CascadeServicesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ItemsRemovedFromPoolComponent,
    ItemsRemovedFromPoolDetailComponent,
    ItemsRemovedFromPoolUpdateComponent,
    ItemsRemovedFromPoolDeleteDialogComponent,
    ItemsRemovedFromPoolDeletePopupComponent
  ],
  entryComponents: [
    ItemsRemovedFromPoolComponent,
    ItemsRemovedFromPoolUpdateComponent,
    ItemsRemovedFromPoolDeleteDialogComponent,
    ItemsRemovedFromPoolDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesItemsRemovedFromPoolModule {}
