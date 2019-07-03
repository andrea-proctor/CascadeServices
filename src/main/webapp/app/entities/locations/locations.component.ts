import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILocations } from 'app/shared/model/locations.model';
import { AccountService } from 'app/core';
import { LocationsService } from './locations.service';

@Component({
  selector: 'jhi-locations',
  templateUrl: './locations.component.html'
})
export class LocationsComponent implements OnInit, OnDestroy {
  locations: ILocations[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected locationsService: LocationsService,
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
      this.locationsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ILocations[]>) => res.ok),
          map((res: HttpResponse<ILocations[]>) => res.body)
        )
        .subscribe((res: ILocations[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.locationsService
      .query()
      .pipe(
        filter((res: HttpResponse<ILocations[]>) => res.ok),
        map((res: HttpResponse<ILocations[]>) => res.body)
      )
      .subscribe(
        (res: ILocations[]) => {
          this.locations = res;
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
    this.registerChangeInLocations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILocations) {
    return item.id;
  }

  registerChangeInLocations() {
    this.eventSubscriber = this.eventManager.subscribe('locationsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
