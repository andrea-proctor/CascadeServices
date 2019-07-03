import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';

@Component({
  selector: 'jhi-items-removed-from-pool-detail',
  templateUrl: './items-removed-from-pool-detail.component.html'
})
export class ItemsRemovedFromPoolDetailComponent implements OnInit {
  itemsRemovedFromPool: IItemsRemovedFromPool;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ itemsRemovedFromPool }) => {
      this.itemsRemovedFromPool = itemsRemovedFromPool;
    });
  }

  previousState() {
    window.history.back();
  }
}
