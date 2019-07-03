import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInventoryValues } from 'app/shared/model/inventory-values.model';

type EntityResponseType = HttpResponse<IInventoryValues>;
type EntityArrayResponseType = HttpResponse<IInventoryValues[]>;

@Injectable({ providedIn: 'root' })
export class InventoryValuesService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-values';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/inventory-values';

  constructor(protected http: HttpClient) {}

  create(inventoryValues: IInventoryValues): Observable<EntityResponseType> {
    return this.http.post<IInventoryValues>(this.resourceUrl, inventoryValues, { observe: 'response' });
  }

  update(inventoryValues: IInventoryValues): Observable<EntityResponseType> {
    return this.http.put<IInventoryValues>(this.resourceUrl, inventoryValues, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInventoryValues>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInventoryValues[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInventoryValues[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
