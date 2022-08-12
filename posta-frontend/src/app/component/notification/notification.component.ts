import { NotificationService } from './../../service/notification.service';
import { Component, OnInit } from '@angular/core';
import { Notification } from 'src/app/model/notification';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  notifications: Notification[]

  constructor(private notificationService: NotificationService) { }

  newNotification= {
    "manager": localStorage.getItem('user'),
    "content": ""
  }

  ngOnInit(): void {
    this.notificationService.getAll(localStorage.getItem('user')).subscribe(
      (notifications: Notification[]) => {
        this.notifications = notifications
        console.log(this.notifications)
      }
    )
  }

  addNotification(){
    this.notificationService.addNotification(this.newNotification).subscribe(
      (m: Notification) => {
        window.location.reload()
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Упс...',
          text: 'Дошло је до грешке!',
        })
      },
    )
  }


  isManager() {
    let role = localStorage.getItem("role");
    if (role == "ROLE_MANAGER"){
      return true;
    }
    return false;
  }

}
