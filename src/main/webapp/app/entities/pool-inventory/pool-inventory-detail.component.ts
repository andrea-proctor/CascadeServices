import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoolInventory } from 'app/shared/model/pool-inventory.model';

@Component({
  selector: 'jhi-pool-inventory-detail',
  templateUrl: './pool-inventory-detail.component.html'
})
export class PoolInventoryDetailComponent implements OnInit {
  poolInventory: IPoolInventory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ poolInventory }) => {
      this.poolInventory = poolInventory;
    });
  }

  previousState() {
    window.history.back();
  }
}
