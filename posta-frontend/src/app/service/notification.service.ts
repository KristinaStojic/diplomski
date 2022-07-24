import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { Notification } from '../model/notification';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notification_url = 'https://localhost:8080/api/notification';

  constructor(private router: Router,
    private _http:HttpClient) { }

  public getAll(user): Observable<Notification[]>{
    return this._http.get<Notification[]>(`${this.notification_url}/getAll/`+ user)
  }

  public addNotification(n): Observable<Notification> {
    return this._http.post<Notification>(`${this.notification_url}/addNotification`, n)
  }
}
