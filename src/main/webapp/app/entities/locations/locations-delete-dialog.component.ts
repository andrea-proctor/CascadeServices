import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocations } from 'app/shared/model/locations.model';
import { LocationsService } from './locations.service';

@Component({
  selector: 'jhi-locations-delete-dialog',
  templateUrl: './locations-delete-dialog.component.html'
})
export class LocationsDeleteDialogComponent {
  locations: ILocations;

  constructor(protected locationsService: LocationsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.locationsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'locationsListModification',
        content: 'Deleted an locations'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-locations-delete-popup',
  template: ''
})
export class LocationsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locations }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LocationsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.locations = locations;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/locations', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/locations', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
