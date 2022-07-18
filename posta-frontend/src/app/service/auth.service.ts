import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from './api.service';
import { catchError, map } from 'rxjs/operators';
import jwt_decode from "jwt-decode";
import { Auth } from '../model/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private auth_url = 'https://localhost:8080/api/auth';


  constructor(
    private apiService: ApiService,
    private router: Router,
    private _http:HttpClient,
  ) { }


  login(user) {
    const loginHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });

    const body = {
      'email': user.email,
      'password': user.password
    };
    console.log(body)
    return this._http.post<Auth>(`${this.auth_url}/login`, body)
      .pipe(map((res: any) => {
        // this.logged = true;
        // this.access_token = res.accessToken;
        let decoded: any = jwt_decode(res.accessToken)
        localStorage.setItem("user", decoded.sub)
        localStorage.setItem("role", decoded.role)
        localStorage.setItem("jwt", res.accessToken);
      }));
  }


  
  logout() { 
    localStorage.clear();
    this.router.navigate(['']);
  }
}
