import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IServiceOrders } from 'app/shared/model/service-orders.model';
import { AccountService } from 'app/core';
import { ServiceOrdersService } from './service-orders.service';

@Component({
  selector: 'jhi-service-orders',
  templateUrl: './service-orders.component.html'
})
export class ServiceOrdersComponent implements OnInit, OnDestroy {
  serviceOrders: IServiceOrders[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected serviceOrdersService: ServiceOrdersService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.serviceOrdersService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IServiceOrders[]>) => res.ok),
          map((res: HttpResponse<IServiceOrders[]>) => res.body)
        )
        .subscribe((res: IServiceOrders[]) => (this.serviceOrders = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.serviceOrdersService
      .query()
      .pipe(
        filter((res: HttpResponse<IServiceOrders[]>) => res.ok),
        map((res: HttpResponse<IServiceOrders[]>) => res.body)
      )
      .subscribe(
        (res: IServiceOrders[]) => {
          this.serviceOrders = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInServiceOrders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IServiceOrders) {
    return item.id;
  }

  registerChangeInServiceOrders() {
    this.eventSubscriber = this.eventManager.subscribe('serviceOrdersListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
