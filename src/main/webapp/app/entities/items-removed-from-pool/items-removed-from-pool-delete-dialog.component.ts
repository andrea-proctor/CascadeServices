import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';
import { ItemsRemovedFromPoolService } from './items-removed-from-pool.service';

@Component({
  selector: 'jhi-items-removed-from-pool-delete-dialog',
  templateUrl: './items-removed-from-pool-delete-dialog.component.html'
})
export class ItemsRemovedFromPoolDeleteDialogComponent {
  itemsRemovedFromPool: IItemsRemovedFromPool;

  constructor(
    protected itemsRemovedFromPoolService: ItemsRemovedFromPoolService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.itemsRemovedFromPoolService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'itemsRemovedFromPoolListModification',
        content: 'Deleted an itemsRemovedFromPool'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-items-removed-from-pool-delete-popup',
  template: ''
})
export class ItemsRemovedFromPoolDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ itemsRemovedFromPool }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ItemsRemovedFromPoolDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.itemsRemovedFromPool = itemsRemovedFromPool;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/items-removed-from-pool', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/items-removed-from-pool', { outlets: { popup: null } }]);
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
