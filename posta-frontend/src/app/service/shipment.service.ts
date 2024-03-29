import { map } from 'rxjs/operators';
import { Shipment } from 'src/app/model/shipment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ShipmentService {
  private shipment_url = 'https://localhost:8080/api/shipment';

  constructor(private _http:HttpClient) { }

  public getAll(): Observable<Shipment[]>{
    return this._http.get<Shipment[]>(`${this.shipment_url}/getAll`)
  }

  public getAllByWorker(email): Observable<any>{
    return this._http.get<any>(`${this.shipment_url}/getAllByWorker/${email}`, email)
  }

  public addShipment(shipment): Observable<Shipment> {
    console.log(shipment)
    return this._http.post<Shipment>(`${this.shipment_url}/addShipment`, shipment)
  }

  public editShipmentStatus(s): Observable<Shipment>{
    console.log(s)
    return this._http.put<Shipment>(`${this.shipment_url}/editShipmentStatus`, s)
  }

  public searchByCode(dto): Observable<any>{
    console.log(dto)
    return this._http.post<any>(this.shipment_url + `/searchByCode`, dto) 
  }

  public recordShipmentInPostOffice(shipment): Observable<Shipment> {
    console.log(shipment)
    return this._http.post<Shipment>(`${this.shipment_url}/recordShipmentInPostOffice`, shipment)
  }

  
  getNumberofShipmentsYearly(dto) {
    return this._http.post(this.shipment_url + `/getNumberofShipmentsYearly`, dto)
    .pipe(map(shipments => {
      return shipments;
    }));   
  }

  getNumberofShipmentsMonthly(dto) {
    return this._http.post(this.shipment_url + `/getNumberofShipmentsMonthly`, dto)
    .pipe(map(shipments => {
      return shipments;
    }));   
  }


  getNumberofShipmentsSelectedPeriod(dto) {
    return this._http.post(this.shipment_url + `/getNumberofShipmentsSelectedPeriod`, dto)
    .pipe(map(shipments => {
      return shipments;
    }));   
  }

  getNumberofShipmentsByTypeSelectedPeriod(dto) {
    return this._http.post(this.shipment_url + `/getNumberofShipmentsByTypeSelectedPeriod`, dto)
    .pipe(map(shipments => {
      return shipments;
    }));   
  }
}
