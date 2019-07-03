import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryValues } from 'app/shared/model/inventory-values.model';
import { InventoryValuesService } from './inventory-values.service';

@Component({
  selector: 'jhi-inventory-values-delete-dialog',
  templateUrl: './inventory-values-delete-dialog.component.html'
})
export class InventoryValuesDeleteDialogComponent {
  inventoryValues: IInventoryValues;

  constructor(
    protected inventoryValuesService: InventoryValuesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inventoryValuesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inventoryValuesListModification',
        content: 'Deleted an inventoryValues'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inventory-values-delete-popup',
  template: ''
})
export class InventoryValuesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventoryValues }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InventoryValuesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.inventoryValues = inventoryValues;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inventory-values', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inventory-values', { outlets: { popup: null } }]);
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
