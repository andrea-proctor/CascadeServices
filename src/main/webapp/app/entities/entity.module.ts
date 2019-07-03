import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'service-orders',
        loadChildren: './service-orders/service-orders.module#CascadeServicesServiceOrdersModule'
      },
      {
        path: 'inventory-values',
        loadChildren: './inventory-values/inventory-values.module#CascadeServicesInventoryValuesModule'
      },
      {
        path: 'supervisors',
        loadChildren: './supervisors/supervisors.module#CascadeServicesSupervisorsModule'
      },
      {
        path: 'locations',
        loadChildren: './locations/locations.module#CascadeServicesLocationsModule'
      },
      {
        path: 'truck-inventory',
        loadChildren: './truck-inventory/truck-inventory.module#CascadeServicesTruckInventoryModule'
      },
      {
        path: 'pool-inventory',
        loadChildren: './pool-inventory/pool-inventory.module#CascadeServicesPoolInventoryModule'
      },
      {
        path: 'items-removed-from-pool',
        loadChildren: './items-removed-from-pool/items-removed-from-pool.module#CascadeServicesItemsRemovedFromPoolModule'
      },
      {
        path: 'purchase-orders',
        loadChildren: './purchase-orders/purchase-orders.module#CascadeServicesPurchaseOrdersModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CascadeServicesEntityModule {}
