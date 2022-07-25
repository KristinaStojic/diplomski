import { Payment } from './../model/payment';
import { PaymentsComponent } from './../component/payments/payments.component';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private payment_url = 'https://localhost:8080/api/payment';


  constructor( private router: Router,
    private _http:HttpClient) { }

    
    public getAll(): Observable<Payment[]>{
      return this._http.get<Payment[]>(`${this.payment_url}/getAll`)
    }
}
