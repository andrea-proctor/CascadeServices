import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITruckInventory, TruckInventory } from 'app/shared/model/truck-inventory.model';
import { TruckInventoryService } from './truck-inventory.service';

@Component({
  selector: 'jhi-truck-inventory-update',
  templateUrl: './truck-inventory-update.component.html'
})
export class TruckInventoryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    date: [],
    truckNumber: [null, [Validators.required]],
    site: [],
    partNumber: [],
    partDescription: [],
    category: [],
    qtyOnHand: [],
    qtyOut: []
  });

  constructor(protected truckInventoryService: TruckInventoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ truckInventory }) => {
      this.updateForm(truckInventory);
    });
  }

  updateForm(truckInventory: ITruckInventory) {
    this.editForm.patchValue({
      id: truckInventory.id,
      date: truckInventory.date,
      truckNumber: truckInventory.truckNumber,
      site: truckInventory.site,
      partNumber: truckInventory.partNumber,
      partDescription: truckInventory.partDescription,
      category: truckInventory.category,
      qtyOnHand: truckInventory.qtyOnHand,
      qtyOut: truckInventory.qtyOut
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const truckInventory = this.createFromForm();
    if (truckInventory.id !== undefined) {
      this.subscribeToSaveResponse(this.truckInventoryService.update(truckInventory));
    } else {
      this.subscribeToSaveResponse(this.truckInventoryService.create(truckInventory));
    }
  }

  private createFromForm(): ITruckInventory {
    return {
      ...new TruckInventory(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value,
      truckNumber: this.editForm.get(['truckNumber']).value,
      site: this.editForm.get(['site']).value,
      partNumber: this.editForm.get(['partNumber']).value,
      partDescription: this.editForm.get(['partDescription']).value,
      category: this.editForm.get(['category']).value,
      qtyOnHand: this.editForm.get(['qtyOnHand']).value,
      qtyOut: this.editForm.get(['qtyOut']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITruckInventory>>) {
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
