import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryValues } from 'app/shared/model/inventory-values.model';

@Component({
  selector: 'jhi-inventory-values-detail',
  templateUrl: './inventory-values-detail.component.html'
})
export class InventoryValuesDetailComponent implements OnInit {
  inventoryValues: IInventoryValues;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventoryValues }) => {
      this.inventoryValues = inventoryValues;
    });
  }

  previousState() {
    window.history.back();
  }
}
