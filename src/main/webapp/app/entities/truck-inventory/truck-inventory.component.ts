import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITruckInventory } from 'app/shared/model/truck-inventory.model';
import { AccountService } from 'app/core';
import { TruckInventoryService } from './truck-inventory.service';

@Component({
  selector: 'jhi-truck-inventory',
  templateUrl: './truck-inventory.component.html'
})
export class TruckInventoryComponent implements OnInit, OnDestroy {
  truckInventories: ITruckInventory[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected truckInventoryService: TruckInventoryService,
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
      this.truckInventoryService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ITruckInventory[]>) => res.ok),
          map((res: HttpResponse<ITruckInventory[]>) => res.body)
        )
        .subscribe((res: ITruckInventory[]) => (this.truckInventories = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.truckInventoryService
      .query()
      .pipe(
        filter((res: HttpResponse<ITruckInventory[]>) => res.ok),
        map((res: HttpResponse<ITruckInventory[]>) => res.body)
      )
      .subscribe(
        (res: ITruckInventory[]) => {
          this.truckInventories = res;
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
    this.registerChangeInTruckInventories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITruckInventory) {
    return item.id;
  }

  registerChangeInTruckInventories() {
    this.eventSubscriber = this.eventManager.subscribe('truckInventoryListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
