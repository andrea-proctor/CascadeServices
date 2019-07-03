import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';
import { AccountService } from 'app/core';
import { ItemsRemovedFromPoolService } from './items-removed-from-pool.service';

@Component({
  selector: 'jhi-items-removed-from-pool',
  templateUrl: './items-removed-from-pool.component.html'
})
export class ItemsRemovedFromPoolComponent implements OnInit, OnDestroy {
  itemsRemovedFromPools: IItemsRemovedFromPool[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected itemsRemovedFromPoolService: ItemsRemovedFromPoolService,
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
      this.itemsRemovedFromPoolService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IItemsRemovedFromPool[]>) => res.ok),
          map((res: HttpResponse<IItemsRemovedFromPool[]>) => res.body)
        )
        .subscribe(
          (res: IItemsRemovedFromPool[]) => (this.itemsRemovedFromPools = res),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.itemsRemovedFromPoolService
      .query()
      .pipe(
        filter((res: HttpResponse<IItemsRemovedFromPool[]>) => res.ok),
        map((res: HttpResponse<IItemsRemovedFromPool[]>) => res.body)
      )
      .subscribe(
        (res: IItemsRemovedFromPool[]) => {
          this.itemsRemovedFromPools = res;
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
    this.registerChangeInItemsRemovedFromPools();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IItemsRemovedFromPool) {
    return item.id;
  }

  registerChangeInItemsRemovedFromPools() {
    this.eventSubscriber = this.eventManager.subscribe('itemsRemovedFromPoolListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
