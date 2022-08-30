import { map } from 'rxjs/operators';
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

    
    public getAll(worker): Observable<any>{
      return this._http.get<any>(`${this.payment_url}/getAll/${worker}`, worker)
    }

    public addPayment(payment): Observable<Payment> {
      return this._http.post<Payment>(`${this.payment_url}/addPayment`, payment)
    }

    getNumberofPaymentsYearly(worker) {
      return this._http.get(this.payment_url + `/getNumberofPaymentsYearly/${worker}`, worker)
      .pipe(map(reservations => {
        return reservations;
      }));   
    }


    getNumberofPaymentsMonthly(dto) {
      return this._http.post(this.payment_url + `/getNumberofPaymentsMonthly`, dto)
      .pipe(map(payments => {
        return payments;
      }));   
    }

    getNumberofPaymentsWeekly(dto) {
      return this._http.post(this.payment_url + `/getNumberofPaymentsWeekly`, dto)
      .pipe(map(payments => {
        return payments;
      }));   
    }

    getAmountofPaymentsWeekly(dto) {
      return this._http.post(this.payment_url + `/getAmountofPaymentsWeekly`, dto)
      .pipe(map(payments => {
        return payments;
      }));   
    }
}
