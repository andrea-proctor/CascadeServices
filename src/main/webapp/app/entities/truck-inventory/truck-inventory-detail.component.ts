import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITruckInventory } from 'app/shared/model/truck-inventory.model';

@Component({
  selector: 'jhi-truck-inventory-detail',
  templateUrl: './truck-inventory-detail.component.html'
})
export class TruckInventoryDetailComponent implements OnInit {
  truckInventory: ITruckInventory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ truckInventory }) => {
      this.truckInventory = truckInventory;
    });
  }

  previousState() {
    window.history.back();
  }
}
