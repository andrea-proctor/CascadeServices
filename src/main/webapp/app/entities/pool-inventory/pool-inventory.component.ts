import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPoolInventory } from 'app/shared/model/pool-inventory.model';
import { AccountService } from 'app/core';
import { PoolInventoryService } from './pool-inventory.service';

@Component({
  selector: 'jhi-pool-inventory',
  templateUrl: './pool-inventory.component.html'
})
export class PoolInventoryComponent implements OnInit, OnDestroy {
  poolInventories: IPoolInventory[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected poolInventoryService: PoolInventoryService,
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
      this.poolInventoryService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPoolInventory[]>) => res.ok),
          map((res: HttpResponse<IPoolInventory[]>) => res.body)
        )
        .subscribe((res: IPoolInventory[]) => (this.poolInventories = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.poolInventoryService
      .query()
      .pipe(
        filter((res: HttpResponse<IPoolInventory[]>) => res.ok),
        map((res: HttpResponse<IPoolInventory[]>) => res.body)
      )
      .subscribe(
        (res: IPoolInventory[]) => {
          this.poolInventories = res;
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
    this.registerChangeInPoolInventories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPoolInventory) {
    return item.id;
  }

  registerChangeInPoolInventories() {
    this.eventSubscriber = this.eventManager.subscribe('poolInventoryListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
