import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CascadeServicesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [CascadeServicesSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [CascadeServicesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesSharedModule {
  static forRoot() {
    return {
      ngModule: CascadeServicesSharedModule
    };
  }
}
