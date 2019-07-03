import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITruckInventory } from 'app/shared/model/truck-inventory.model';
import { TruckInventoryService } from './truck-inventory.service';

@Component({
  selector: 'jhi-truck-inventory-delete-dialog',
  templateUrl: './truck-inventory-delete-dialog.component.html'
})
export class TruckInventoryDeleteDialogComponent {
  truckInventory: ITruckInventory;

  constructor(
    protected truckInventoryService: TruckInventoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.truckInventoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'truckInventoryListModification',
        content: 'Deleted an truckInventory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-truck-inventory-delete-popup',
  template: ''
})
export class TruckInventoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ truckInventory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TruckInventoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.truckInventory = truckInventory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/truck-inventory', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/truck-inventory', { outlets: { popup: null } }]);
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
