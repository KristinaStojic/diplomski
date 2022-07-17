import { AuthService } from './service/auth.service';
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Auth } from './model/auth';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'posta-frontend';
  constructor(private _http:HttpClient,
    private authService: AuthService) { }


  login(){
    const loginHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });

    var dto = {
      "email": "stojic.kris@gmail.com",
      "password": "kris"
    }
    console.log(dto)
    // this._http.post<Auth>(`http://localhost:8080/api/auth/login`, JSON.stringify(dto)).subscribe(data=>{
    //   console.log(data)
    // })

    this.authService.login(dto)
    .subscribe(data => {
      console.log(data)
         
      },
      error => {
        // this.submitted = false;
        // this.notification = {msgType: 'error', msgBody: 'Incorrect username/password or account is not verified.'};
      });

  }
}


