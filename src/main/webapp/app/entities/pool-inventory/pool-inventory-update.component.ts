import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPoolInventory, PoolInventory } from 'app/shared/model/pool-inventory.model';
import { PoolInventoryService } from './pool-inventory.service';

@Component({
  selector: 'jhi-pool-inventory-update',
  templateUrl: './pool-inventory-update.component.html'
})
export class PoolInventoryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    date: [],
    partnumber: [],
    partdescription: [],
    qtyin: [],
    qtyout: [],
    language: []
  });

  constructor(protected poolInventoryService: PoolInventoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ poolInventory }) => {
      this.updateForm(poolInventory);
    });
  }

  updateForm(poolInventory: IPoolInventory) {
    this.editForm.patchValue({
      id: poolInventory.id,
      date: poolInventory.date,
      partnumber: poolInventory.partnumber,
      partdescription: poolInventory.partdescription,
      qtyin: poolInventory.qtyin,
      qtyout: poolInventory.qtyout,
      language: poolInventory.language
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const poolInventory = this.createFromForm();
    if (poolInventory.id !== undefined) {
      this.subscribeToSaveResponse(this.poolInventoryService.update(poolInventory));
    } else {
      this.subscribeToSaveResponse(this.poolInventoryService.create(poolInventory));
    }
  }

  private createFromForm(): IPoolInventory {
    return {
      ...new PoolInventory(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value,
      partnumber: this.editForm.get(['partnumber']).value,
      partdescription: this.editForm.get(['partdescription']).value,
      qtyin: this.editForm.get(['qtyin']).value,
      qtyout: this.editForm.get(['qtyout']).value,
      language: this.editForm.get(['language']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoolInventory>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
