import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IServiceOrders, ServiceOrders } from 'app/shared/model/service-orders.model';
import { ServiceOrdersService } from './service-orders.service';

@Component({
  selector: 'jhi-service-orders-update',
  templateUrl: './service-orders-update.component.html'
})
export class ServiceOrdersUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    site: [],
    supervisor: [],
    soNumber: [null, [Validators.required]],
    partNumber: [],
    partDescription: [],
    repairCost: [],
    freightCharge: [],
    totalCharge: [],
    notes: []
  });

  constructor(protected serviceOrdersService: ServiceOrdersService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceOrders }) => {
      this.updateForm(serviceOrders);
    });
  }

  updateForm(serviceOrders: IServiceOrders) {
    this.editForm.patchValue({
      id: serviceOrders.id,
      site: serviceOrders.site,
      supervisor: serviceOrders.supervisor,
      soNumber: serviceOrders.soNumber,
      partNumber: serviceOrders.partNumber,
      partDescription: serviceOrders.partDescription,
      repairCost: serviceOrders.repairCost,
      freightCharge: serviceOrders.freightCharge,
      totalCharge: serviceOrders.totalCharge,
      notes: serviceOrders.notes
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceOrders = this.createFromForm();
    if (serviceOrders.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceOrdersService.update(serviceOrders));
    } else {
      this.subscribeToSaveResponse(this.serviceOrdersService.create(serviceOrders));
    }
  }

  private createFromForm(): IServiceOrders {
    return {
      ...new ServiceOrders(),
      id: this.editForm.get(['id']).value,
      site: this.editForm.get(['site']).value,
      supervisor: this.editForm.get(['supervisor']).value,
      soNumber: this.editForm.get(['soNumber']).value,
      partNumber: this.editForm.get(['partNumber']).value,
      partDescription: this.editForm.get(['partDescription']).value,
      repairCost: this.editForm.get(['repairCost']).value,
      freightCharge: this.editForm.get(['freightCharge']).value,
      totalCharge: this.editForm.get(['totalCharge']).value,
      notes: this.editForm.get(['notes']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceOrders>>) {
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
