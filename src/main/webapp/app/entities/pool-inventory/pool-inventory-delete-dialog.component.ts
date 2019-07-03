import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoolInventory } from 'app/shared/model/pool-inventory.model';
import { PoolInventoryService } from './pool-inventory.service';

@Component({
  selector: 'jhi-pool-inventory-delete-dialog',
  templateUrl: './pool-inventory-delete-dialog.component.html'
})
export class PoolInventoryDeleteDialogComponent {
  poolInventory: IPoolInventory;

  constructor(
    protected poolInventoryService: PoolInventoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.poolInventoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'poolInventoryListModification',
        content: 'Deleted an poolInventory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pool-inventory-delete-popup',
  template: ''
})
export class PoolInventoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ poolInventory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PoolInventoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.poolInventory = poolInventory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pool-inventory', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pool-inventory', { outlets: { popup: null } }]);
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
