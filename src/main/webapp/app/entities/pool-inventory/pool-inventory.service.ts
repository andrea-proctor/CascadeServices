import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPoolInventory } from 'app/shared/model/pool-inventory.model';

type EntityResponseType = HttpResponse<IPoolInventory>;
type EntityArrayResponseType = HttpResponse<IPoolInventory[]>;

@Injectable({ providedIn: 'root' })
export class PoolInventoryService {
  public resourceUrl = SERVER_API_URL + 'api/pool-inventories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/pool-inventories';

  constructor(protected http: HttpClient) {}

  create(poolInventory: IPoolInventory): Observable<EntityResponseType> {
    return this.http.post<IPoolInventory>(this.resourceUrl, poolInventory, { observe: 'response' });
  }

  update(poolInventory: IPoolInventory): Observable<EntityResponseType> {
    return this.http.put<IPoolInventory>(this.resourceUrl, poolInventory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPoolInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPoolInventory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPoolInventory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
