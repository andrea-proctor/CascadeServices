import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IInventoryValues, InventoryValues } from 'app/shared/model/inventory-values.model';
import { InventoryValuesService } from './inventory-values.service';

@Component({
  selector: 'jhi-inventory-values-update',
  templateUrl: './inventory-values-update.component.html'
})
export class InventoryValuesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    partNumber: [],
    partDescription: [],
    valueEach: []
  });

  constructor(
    protected inventoryValuesService: InventoryValuesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inventoryValues }) => {
      this.updateForm(inventoryValues);
    });
  }

  updateForm(inventoryValues: IInventoryValues) {
    this.editForm.patchValue({
      id: inventoryValues.id,
      partNumber: inventoryValues.partNumber,
      partDescription: inventoryValues.partDescription,
      valueEach: inventoryValues.valueEach
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inventoryValues = this.createFromForm();
    if (inventoryValues.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryValuesService.update(inventoryValues));
    } else {
      this.subscribeToSaveResponse(this.inventoryValuesService.create(inventoryValues));
    }
  }

  private createFromForm(): IInventoryValues {
    return {
      ...new InventoryValues(),
      id: this.editForm.get(['id']).value,
      partNumber: this.editForm.get(['partNumber']).value,
      partDescription: this.editForm.get(['partDescription']).value,
      valueEach: this.editForm.get(['valueEach']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryValues>>) {
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
