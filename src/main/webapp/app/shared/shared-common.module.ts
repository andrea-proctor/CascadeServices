import { NgModule } from '@angular/core';

import { CascadeServicesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [CascadeServicesSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [CascadeServicesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CascadeServicesSharedCommonModule {}
