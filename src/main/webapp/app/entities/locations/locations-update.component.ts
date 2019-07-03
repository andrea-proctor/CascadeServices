import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILocations, Locations } from 'app/shared/model/locations.model';
import { LocationsService } from './locations.service';

@Component({
  selector: 'jhi-locations-update',
  templateUrl: './locations-update.component.html'
})
export class LocationsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    site: [],
    street: [],
    zipCode: [],
    city: [],
    state: []
  });

  constructor(protected locationsService: LocationsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ locations }) => {
      this.updateForm(locations);
    });
  }

  updateForm(locations: ILocations) {
    this.editForm.patchValue({
      id: locations.id,
      site: locations.site,
      street: locations.street,
      zipCode: locations.zipCode,
      city: locations.city,
      state: locations.state
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const locations = this.createFromForm();
    if (locations.id !== undefined) {
      this.subscribeToSaveResponse(this.locationsService.update(locations));
    } else {
      this.subscribeToSaveResponse(this.locationsService.create(locations));
    }
  }

  private createFromForm(): ILocations {
    return {
      ...new Locations(),
      id: this.editForm.get(['id']).value,
      site: this.editForm.get(['site']).value,
      street: this.editForm.get(['street']).value,
      zipCode: this.editForm.get(['zipCode']).value,
      city: this.editForm.get(['city']).value,
      state: this.editForm.get(['state']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocations>>) {
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
