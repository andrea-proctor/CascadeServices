import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CascadeServicesSharedModule } from 'app/shared';
import {
  ServiceOrdersComponent,
  ServiceOrdersDetailComponent,
  ServiceOrdersUpdateComponent,
  ServiceOrdersDeletePopupComponent,
  ServiceOrdersDeleteDialogComponent,
  serviceOrdersRoute,
  serviceOrdersPopupRoute
} from './';

const ENTITY_STATES = [...serviceOrdersRoute, ...serviceOrdersPopupRoute];

@NgModule({
  imports: [CascadeServicesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceOrdersComponent,
    ServiceOrdersDetailComponent,
    ServiceOrdersUpdateComponent,
    ServiceOrdersDeleteDialogComponent,
    ServiceOrdersDeletePopupComponent
  ],
  entryComponents: [
    ServiceOrdersComponent,
    ServiceOrdersUpdateComponent,
    ServiceOrdersDeleteDialogComponent,
    ServiceOrdersDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesServiceOrdersModule {}
