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
    // return this._http.post(this.shipment_url + `/searchByCode`, dto)
    //   .pipe(map(s => {
    //     return s;
    //   }));  
  }

  public recordShipmentInPostOffice(shipment): Observable<Shipment> {
    console.log(shipment)
    return this._http.post<Shipment>(`${this.shipment_url}/recordShipmentInPostOffice`, shipment)
  }
}
