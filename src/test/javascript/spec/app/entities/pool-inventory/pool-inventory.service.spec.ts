/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { PoolInventoryService } from 'app/entities/pool-inventory/pool-inventory.service';
import { IPoolInventory, PoolInventory, Language } from 'app/shared/model/pool-inventory.model';

describe('Service Tests', () => {
  describe('PoolInventory Service', () => {
    let injector: TestBed;
    let service: PoolInventoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IPoolInventory;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PoolInventoryService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PoolInventory(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, Language.FRENCH);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a PoolInventory', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new PoolInventory(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PoolInventory', async () => {
        const returnedFromService = Object.assign(
          {
            date: 'BBBBBB',
            partnumber: 'BBBBBB',
            partdescription: 'BBBBBB',
            qtyin: 1,
            qtyout: 1,
            language: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of PoolInventory', async () => {
        const returnedFromService = Object.assign(
          {
            date: 'BBBBBB',
            partnumber: 'BBBBBB',
            partdescription: 'BBBBBB',
            qtyin: 1,
            qtyout: 1,
            language: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PoolInventory', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
