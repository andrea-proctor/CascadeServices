import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceOrders } from 'app/shared/model/service-orders.model';
import { ServiceOrdersService } from './service-orders.service';

@Component({
  selector: 'jhi-service-orders-delete-dialog',
  templateUrl: './service-orders-delete-dialog.component.html'
})
export class ServiceOrdersDeleteDialogComponent {
  serviceOrders: IServiceOrders;

  constructor(
    protected serviceOrdersService: ServiceOrdersService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceOrdersService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceOrdersListModification',
        content: 'Deleted an serviceOrders'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-orders-delete-popup',
  template: ''
})
export class ServiceOrdersDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOrders }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceOrdersDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceOrders = serviceOrders;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-orders', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-orders', { outlets: { popup: null } }]);
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
