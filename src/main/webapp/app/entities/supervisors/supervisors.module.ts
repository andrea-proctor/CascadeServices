import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CascadeServicesSharedModule } from 'app/shared';
import {
  SupervisorsComponent,
  SupervisorsDetailComponent,
  SupervisorsUpdateComponent,
  SupervisorsDeletePopupComponent,
  SupervisorsDeleteDialogComponent,
  supervisorsRoute,
  supervisorsPopupRoute
} from './';

const ENTITY_STATES = [...supervisorsRoute, ...supervisorsPopupRoute];

@NgModule({
  imports: [CascadeServicesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SupervisorsComponent,
    SupervisorsDetailComponent,
    SupervisorsUpdateComponent,
    SupervisorsDeleteDialogComponent,
    SupervisorsDeletePopupComponent
  ],
  entryComponents: [SupervisorsComponent, SupervisorsUpdateComponent, SupervisorsDeleteDialogComponent, SupervisorsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesSupervisorsModule {}
