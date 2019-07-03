import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemsRemovedFromPool } from 'app/shared/model/items-removed-from-pool.model';

type EntityResponseType = HttpResponse<IItemsRemovedFromPool>;
type EntityArrayResponseType = HttpResponse<IItemsRemovedFromPool[]>;

@Injectable({ providedIn: 'root' })
export class ItemsRemovedFromPoolService {
  public resourceUrl = SERVER_API_URL + 'api/items-removed-from-pools';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/items-removed-from-pools';

  constructor(protected http: HttpClient) {}

  create(itemsRemovedFromPool: IItemsRemovedFromPool): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemsRemovedFromPool);
    return this.http
      .post<IItemsRemovedFromPool>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(itemsRemovedFromPool: IItemsRemovedFromPool): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemsRemovedFromPool);
    return this.http
      .put<IItemsRemovedFromPool>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IItemsRemovedFromPool>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IItemsRemovedFromPool[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IItemsRemovedFromPool[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(itemsRemovedFromPool: IItemsRemovedFromPool): IItemsRemovedFromPool {
    const copy: IItemsRemovedFromPool = Object.assign({}, itemsRemovedFromPool, {
      hireDate:
        itemsRemovedFromPool.hireDate != null && itemsRemovedFromPool.hireDate.isValid() ? itemsRemovedFromPool.hireDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hireDate = res.body.hireDate != null ? moment(res.body.hireDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((itemsRemovedFromPool: IItemsRemovedFromPool) => {
        itemsRemovedFromPool.hireDate = itemsRemovedFromPool.hireDate != null ? moment(itemsRemovedFromPool.hireDate) : null;
      });
    }
    return res;
  }
}
