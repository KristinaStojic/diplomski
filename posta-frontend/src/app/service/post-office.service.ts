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
}
