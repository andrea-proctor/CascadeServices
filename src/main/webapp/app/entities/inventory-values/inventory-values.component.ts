import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInventoryValues } from 'app/shared/model/inventory-values.model';
import { AccountService } from 'app/core';
import { InventoryValuesService } from './inventory-values.service';

@Component({
  selector: 'jhi-inventory-values',
  templateUrl: './inventory-values.component.html'
})
export class InventoryValuesComponent implements OnInit, OnDestroy {
  inventoryValues: IInventoryValues[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected inventoryValuesService: InventoryValuesService,
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
      this.inventoryValuesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IInventoryValues[]>) => res.ok),
          map((res: HttpResponse<IInventoryValues[]>) => res.body)
        )
        .subscribe((res: IInventoryValues[]) => (this.inventoryValues = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.inventoryValuesService
      .query()
      .pipe(
        filter((res: HttpResponse<IInventoryValues[]>) => res.ok),
        map((res: HttpResponse<IInventoryValues[]>) => res.body)
      )
      .subscribe(
        (res: IInventoryValues[]) => {
          this.inventoryValues = res;
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
    this.registerChangeInInventoryValues();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInventoryValues) {
    return item.id;
  }

  registerChangeInInventoryValues() {
    this.eventSubscriber = this.eventManager.subscribe('inventoryValuesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
