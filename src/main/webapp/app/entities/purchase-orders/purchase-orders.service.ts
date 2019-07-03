import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';

type EntityResponseType = HttpResponse<IPurchaseOrders>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrders[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrdersService {
  public resourceUrl = SERVER_API_URL + 'api/purchase-orders';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/purchase-orders';

  constructor(protected http: HttpClient) {}

  create(purchaseOrders: IPurchaseOrders): Observable<EntityResponseType> {
    return this.http.post<IPurchaseOrders>(this.resourceUrl, purchaseOrders, { observe: 'response' });
  }

  update(purchaseOrders: IPurchaseOrders): Observable<EntityResponseType> {
    return this.http.put<IPurchaseOrders>(this.resourceUrl, purchaseOrders, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPurchaseOrders>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPurchaseOrders[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPurchaseOrders[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
