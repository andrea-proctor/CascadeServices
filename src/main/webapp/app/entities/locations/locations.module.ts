import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CascadeServicesSharedModule } from 'app/shared';
import {
  LocationsComponent,
  LocationsDetailComponent,
  LocationsUpdateComponent,
  LocationsDeletePopupComponent,
  LocationsDeleteDialogComponent,
  locationsRoute,
  locationsPopupRoute
} from './';

const ENTITY_STATES = [...locationsRoute, ...locationsPopupRoute];

@NgModule({
  imports: [CascadeServicesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LocationsComponent,
    LocationsDetailComponent,
    LocationsUpdateComponent,
    LocationsDeleteDialogComponent,
    LocationsDeletePopupComponent
  ],
  entryComponents: [LocationsComponent, LocationsUpdateComponent, LocationsDeleteDialogComponent, LocationsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesLocationsModule {}
