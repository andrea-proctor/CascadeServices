import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IItemsRemovedFromPool, ItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';
import { ItemsRemovedFromPoolService } from './items-removed-from-pool.service';

@Component({
  selector: 'jhi-items-removed-from-pool-update',
  templateUrl: './items-removed-from-pool-update.component.html'
})
export class ItemsRemovedFromPoolUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    phoneNumber: [],
    hireDate: [],
    salary: [],
    commissionPct: []
  });

  constructor(
    protected itemsRemovedFromPoolService: ItemsRemovedFromPoolService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ itemsRemovedFromPool }) => {
      this.updateForm(itemsRemovedFromPool);
    });
  }

  updateForm(itemsRemovedFromPool: IItemsRemovedFromPool) {
    this.editForm.patchValue({
      id: itemsRemovedFromPool.id,
      firstName: itemsRemovedFromPool.firstName,
      lastName: itemsRemovedFromPool.lastName,
      email: itemsRemovedFromPool.email,
      phoneNumber: itemsRemovedFromPool.phoneNumber,
      hireDate: itemsRemovedFromPool.hireDate != null ? itemsRemovedFromPool.hireDate.format(DATE_TIME_FORMAT) : null,
      salary: itemsRemovedFromPool.salary,
      commissionPct: itemsRemovedFromPool.commissionPct
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const itemsRemovedFromPool = this.createFromForm();
    if (itemsRemovedFromPool.id !== undefined) {
      this.subscribeToSaveResponse(this.itemsRemovedFromPoolService.update(itemsRemovedFromPool));
    } else {
      this.subscribeToSaveResponse(this.itemsRemovedFromPoolService.create(itemsRemovedFromPool));
    }
  }

  private createFromForm(): IItemsRemovedFromPool {
    return {
      ...new ItemsRemovedFromPool(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      email: this.editForm.get(['email']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      hireDate: this.editForm.get(['hireDate']).value != null ? moment(this.editForm.get(['hireDate']).value, DATE_TIME_FORMAT) : undefined,
      salary: this.editForm.get(['salary']).value,
      commissionPct: this.editForm.get(['commissionPct']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemsRemovedFromPool>>) {
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
