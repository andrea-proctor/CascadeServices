import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocations } from 'app/shared/model/locations.model';

type EntityResponseType = HttpResponse<ILocations>;
type EntityArrayResponseType = HttpResponse<ILocations[]>;

@Injectable({ providedIn: 'root' })
export class LocationsService {
  public resourceUrl = SERVER_API_URL + 'api/locations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/locations';

  constructor(protected http: HttpClient) {}

  create(locations: ILocations): Observable<EntityResponseType> {
    return this.http.post<ILocations>(this.resourceUrl, locations, { observe: 'response' });
  }

  update(locations: ILocations): Observable<EntityResponseType> {
    return this.http.put<ILocations>(this.resourceUrl, locations, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocations>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocations[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocations[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
