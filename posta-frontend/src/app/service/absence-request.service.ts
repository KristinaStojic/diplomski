import { Observable } from 'rxjs';
import { AbsenceRequest } from './../model/absence-request';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AbsenceRequestService {
  private absence_request_url = 'https://localhost:8080/api/absence_request';

  constructor(private router: Router,
    private _http:HttpClient) { }

    public getAll(): Observable<AbsenceRequest[]>{
      return this._http.get<AbsenceRequest[]>(`${this.absence_request_url}/getAll/` + localStorage.getItem('user'))
    }

    public getAllByWorker(): Observable<AbsenceRequest[]>{
      return this._http.get<AbsenceRequest[]>(`${this.absence_request_url}/getByWorker/` + localStorage.getItem('user'))
    }

    public addAbsenceRequest(request): Observable<AbsenceRequest> {
      return this._http.post<AbsenceRequest>(`${this.absence_request_url}/addAbsenceRequest`, request)
    }

    public processRequest(dto): Observable<any> {
      console.log(dto)
      return this._http.put<any>(this.absence_request_url + `/processAbsenceRequest`, dto)
    }

}
