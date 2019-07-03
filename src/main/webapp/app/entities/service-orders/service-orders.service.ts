import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceOrders } from 'app/shared/model/service-orders.model';

type EntityResponseType = HttpResponse<IServiceOrders>;
type EntityArrayResponseType = HttpResponse<IServiceOrders[]>;

@Injectable({ providedIn: 'root' })
export class ServiceOrdersService {
  public resourceUrl = SERVER_API_URL + 'api/service-orders';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/service-orders';

  constructor(protected http: HttpClient) {}

  create(serviceOrders: IServiceOrders): Observable<EntityResponseType> {
    return this.http.post<IServiceOrders>(this.resourceUrl, serviceOrders, { observe: 'response' });
  }

  update(serviceOrders: IServiceOrders): Observable<EntityResponseType> {
    return this.http.put<IServiceOrders>(this.resourceUrl, serviceOrders, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceOrders>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceOrders[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceOrders[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
