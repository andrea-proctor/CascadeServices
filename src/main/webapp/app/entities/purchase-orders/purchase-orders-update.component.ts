import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPurchaseOrders, PurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from './purchase-orders.service';

@Component({
  selector: 'jhi-purchase-orders-update',
  templateUrl: './purchase-orders-update.component.html'
})
export class PurchaseOrdersUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    site: [],
    supervisor: [],
    poNumber: [null, [Validators.required]],
    partNumber: [],
    partDescription: [],
    qtyReplaced: [],
    replacementCostEach: [],
    freightCharge: [],
    totalCharge: [],
    notes: []
  });

  constructor(protected purchaseOrdersService: PurchaseOrdersService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ purchaseOrders }) => {
      this.updateForm(purchaseOrders);
    });
  }

  updateForm(purchaseOrders: IPurchaseOrders) {
    this.editForm.patchValue({
      id: purchaseOrders.id,
      site: purchaseOrders.site,
      supervisor: purchaseOrders.supervisor,
      poNumber: purchaseOrders.poNumber,
      partNumber: purchaseOrders.partNumber,
      partDescription: purchaseOrders.partDescription,
      qtyReplaced: purchaseOrders.qtyReplaced,
      replacementCostEach: purchaseOrders.replacementCostEach,
      freightCharge: purchaseOrders.freightCharge,
      totalCharge: purchaseOrders.totalCharge,
      notes: purchaseOrders.notes
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const purchaseOrders = this.createFromForm();
    if (purchaseOrders.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseOrdersService.update(purchaseOrders));
    } else {
      this.subscribeToSaveResponse(this.purchaseOrdersService.create(purchaseOrders));
    }
  }

  private createFromForm(): IPurchaseOrders {
    return {
      ...new PurchaseOrders(),
      id: this.editForm.get(['id']).value,
      site: this.editForm.get(['site']).value,
      supervisor: this.editForm.get(['supervisor']).value,
      poNumber: this.editForm.get(['poNumber']).value,
      partNumber: this.editForm.get(['partNumber']).value,
      partDescription: this.editForm.get(['partDescription']).value,
      qtyReplaced: this.editForm.get(['qtyReplaced']).value,
      replacementCostEach: this.editForm.get(['replacementCostEach']).value,
      freightCharge: this.editForm.get(['freightCharge']).value,
      totalCharge: this.editForm.get(['totalCharge']).value,
      notes: this.editForm.get(['notes']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrders>>) {
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
