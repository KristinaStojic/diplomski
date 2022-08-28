import { Payoff } from './../model/payoff';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PayoffService {

  private payoff_url = 'https://localhost:8080/api/payoff';

  constructor(private _http:HttpClient) { }

  public getAll(): Observable<Payoff[]>{
    return this._http.get<Payoff[]>(`${this.payoff_url}/getAll/` + localStorage.getItem('user'))
  }

  public search(dto): Observable<any>{
    return this._http.post<any>(this.payoff_url + `/search`, dto) 
  }

  public payOff(dto): Observable<Boolean>{
    return this._http.put<Boolean>(this.payoff_url + `/changePaidOffStatus`, dto)
  }

  public addPayoff(p): Observable<Payoff> {
    console.log(p)
    return this._http.post<Payoff>(`${this.payoff_url}/addPayoff`, p)
  }

}


