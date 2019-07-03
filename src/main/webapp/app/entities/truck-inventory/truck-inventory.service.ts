import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITruckInventory } from 'app/shared/model/truck-inventory.model';

type EntityResponseType = HttpResponse<ITruckInventory>;
type EntityArrayResponseType = HttpResponse<ITruckInventory[]>;

@Injectable({ providedIn: 'root' })
export class TruckInventoryService {
  public resourceUrl = SERVER_API_URL + 'api/truck-inventories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/truck-inventories';

  constructor(protected http: HttpClient) {}

  create(truckInventory: ITruckInventory): Observable<EntityResponseType> {
    return this.http.post<ITruckInventory>(this.resourceUrl, truckInventory, { observe: 'response' });
  }

  update(truckInventory: ITruckInventory): Observable<EntityResponseType> {
    return this.http.put<ITruckInventory>(this.resourceUrl, truckInventory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITruckInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITruckInventory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITruckInventory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
