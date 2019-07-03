import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupervisors } from 'app/shared/model/supervisors.model';

type EntityResponseType = HttpResponse<ISupervisors>;
type EntityArrayResponseType = HttpResponse<ISupervisors[]>;

@Injectable({ providedIn: 'root' })
export class SupervisorsService {
  public resourceUrl = SERVER_API_URL + 'api/supervisors';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/supervisors';

  constructor(protected http: HttpClient) {}

  create(supervisors: ISupervisors): Observable<EntityResponseType> {
    return this.http.post<ISupervisors>(this.resourceUrl, supervisors, { observe: 'response' });
  }

  update(supervisors: ISupervisors): Observable<EntityResponseType> {
    return this.http.put<ISupervisors>(this.resourceUrl, supervisors, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISupervisors>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISupervisors[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISupervisors[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
