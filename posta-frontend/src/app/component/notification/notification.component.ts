import { NotificationService } from './../../service/notification.service';
import { Component, OnInit } from '@angular/core';
import { Notification } from 'src/app/model/notification';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  notifications: Notification[]

  constructor(private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.notificationService.getAll(localStorage.getItem('user')).subscribe(
      (notifications: Notification[]) => {
        this.notifications = notifications
        console.log(this.notifications)
      }
    )
  }

}
