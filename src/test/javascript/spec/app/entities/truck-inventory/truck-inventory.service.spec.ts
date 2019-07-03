/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { TruckInventoryService } from 'app/entities/truck-inventory/truck-inventory.service';
import { ITruckInventory, TruckInventory } from 'app/shared/model/truck-inventory.model';

describe('Service Tests', () => {
  describe('TruckInventory Service', () => {
    let injector: TestBed;
    let service: TruckInventoryService;
    let httpMock: HttpTestingController;
    let elemDefault: ITruckInventory;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TruckInventoryService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TruckInventory(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0);
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

      it('should create a TruckInventory', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new TruckInventory(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a TruckInventory', async () => {
        const returnedFromService = Object.assign(
          {
            date: 'BBBBBB',
            truckNumber: 'BBBBBB',
            site: 'BBBBBB',
            partNumber: 'BBBBBB',
            partDescription: 'BBBBBB',
            category: 'BBBBBB',
            qtyOnHand: 1,
            qtyOut: 1
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

      it('should return a list of TruckInventory', async () => {
        const returnedFromService = Object.assign(
          {
            date: 'BBBBBB',
            truckNumber: 'BBBBBB',
            site: 'BBBBBB',
            partNumber: 'BBBBBB',
            partDescription: 'BBBBBB',
            category: 'BBBBBB',
            qtyOnHand: 1,
            qtyOut: 1
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

      it('should delete a TruckInventory', async () => {
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
