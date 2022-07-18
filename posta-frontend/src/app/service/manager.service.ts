import { Manager } from './../model/manager';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {
  private manager_url = 'https://localhost:8080/api/manager';

  constructor( private router: Router,
    private _http:HttpClient) { }

    public getAll(): Observable<Manager[]>{
      return this._http.get<Manager[]>(`${this.manager_url}/getAll`)
    }

    public addMaganer(manager): Observable<Manager> {
      return this._http.post<Manager>(`${this.manager_url}/addManager`, manager)
    }

    public editManager(manager): Observable<Manager>{
      console.log(manager)
      return this._http.put<Manager>(`${this.manager_url}/editManager`, manager)
    }

    public delete(id): Observable <Manager> {
      return this._http.delete<Manager>(`${this.manager_url}/deleteManager/` + id)
    }
}
