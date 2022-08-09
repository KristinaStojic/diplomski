import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Shipment } from '../model/shipment';

@Injectable({
  providedIn: 'root'
})
export class ShipmentService {
  private shipment_url = 'https://localhost:8080/api/shipment';

  constructor(private _http:HttpClient) { }

  public getAll(): Observable<Shipment[]>{
    return this._http.get<Shipment[]>(`${this.shipment_url}/getAll`)
  }

  public addShipment(shipment): Observable<Shipment> {
    console.log(shipment)
    return this._http.post<Shipment>(`${this.shipment_url}/addShipment`, shipment)
  }
}