import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISupervisors, Supervisors } from 'app/shared/model/supervisors.model';
import { SupervisorsService } from './supervisors.service';

@Component({
  selector: 'jhi-supervisors-update',
  templateUrl: './supervisors-update.component.html'
})
export class SupervisorsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    site: []
  });

  constructor(protected supervisorsService: SupervisorsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ supervisors }) => {
      this.updateForm(supervisors);
    });
  }

  updateForm(supervisors: ISupervisors) {
    this.editForm.patchValue({
      id: supervisors.id,
      firstName: supervisors.firstName,
      lastName: supervisors.lastName,
      site: supervisors.site
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const supervisors = this.createFromForm();
    if (supervisors.id !== undefined) {
      this.subscribeToSaveResponse(this.supervisorsService.update(supervisors));
    } else {
      this.subscribeToSaveResponse(this.supervisorsService.create(supervisors));
    }
  }

  private createFromForm(): ISupervisors {
    return {
      ...new Supervisors(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      site: this.editForm.get(['site']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupervisors>>) {
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
