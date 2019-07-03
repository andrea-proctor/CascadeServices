import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocations } from 'app/shared/model/locations.model';

@Component({
  selector: 'jhi-locations-detail',
  templateUrl: './locations-detail.component.html'
})
export class LocationsDetailComponent implements OnInit {
  locations: ILocations;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locations }) => {
      this.locations = locations;
    });
  }

  previousState() {
    window.history.back();
  }
}
