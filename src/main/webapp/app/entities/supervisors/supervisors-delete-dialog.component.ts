import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupervisors } from 'app/shared/model/supervisors.model';
import { SupervisorsService } from './supervisors.service';

@Component({
  selector: 'jhi-supervisors-delete-dialog',
  templateUrl: './supervisors-delete-dialog.component.html'
})
export class SupervisorsDeleteDialogComponent {
  supervisors: ISupervisors;

  constructor(
    protected supervisorsService: SupervisorsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supervisorsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'supervisorsListModification',
        content: 'Deleted an supervisors'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-supervisors-delete-popup',
  template: ''
})
export class SupervisorsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supervisors }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SupervisorsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.supervisors = supervisors;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/supervisors', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/supervisors', { outlets: { popup: null } }]);
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
