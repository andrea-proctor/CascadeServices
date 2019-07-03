import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupervisors } from 'app/shared/model/supervisors.model';

@Component({
  selector: 'jhi-supervisors-detail',
  templateUrl: './supervisors-detail.component.html'
})
export class SupervisorsDetailComponent implements OnInit {
  supervisors: ISupervisors;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supervisors }) => {
      this.supervisors = supervisors;
    });
  }

  previousState() {
    window.history.back();
  }
}
