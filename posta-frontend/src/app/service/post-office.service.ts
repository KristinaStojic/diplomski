import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { PostOffice } from '../model/post-office';

@Injectable({
  providedIn: 'root'
})
export class PostOfficeService {
  private postOffice_url = 'https://localhost:8080/api/postOffice';

  constructor( private router: Router,
    private _http:HttpClient) { }


    public getAll(): Observable<PostOffice[]>{
      return this._http.get<PostOffice[]>(`${this.postOffice_url}/getAll`)
    }

    public addMaganer(postOffice): Observable<PostOffice> {
      return this._http.post<PostOffice>(`${this.postOffice_url}/addPostOffice`, postOffice)
    }

    public editManager(postOffice): Observable<PostOffice>{
      console.log(postOffice)
      return this._http.put<PostOffice>(`${this.postOffice_url}/editPostOffice`, postOffice)
    }

    public delete(id): Observable <any> {
      return this._http.delete<any>(`${this.postOffice_url}/deletePostOffice/` + id)
    }
}
