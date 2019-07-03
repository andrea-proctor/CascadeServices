import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceOrders } from 'app/shared/model/service-orders.model';

@Component({
  selector: 'jhi-service-orders-detail',
  templateUrl: './service-orders-detail.component.html'
})
export class ServiceOrdersDetailComponent implements OnInit {
  serviceOrders: IServiceOrders;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOrders }) => {
      this.serviceOrders = serviceOrders;
    });
  }

  previousState() {
    window.history.back();
  }
}
