import { ManagerService } from './../../service/manager.service';
import { Component, OnInit } from '@angular/core';
import { Manager } from 'src/app/model/manager';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.scss']
})
export class AdminHomeComponent implements OnInit {

  managers: Manager[]

  manager= {
    "name": "",
    "surname": "",
    "phoneNumber": "",
    "email": ""

  }
  constructor(private managerService: ManagerService) { }

  ngOnInit(): void {
    this.managerService.getAll().subscribe(
      (managers: Manager[]) => {
        this.managers = managers
        console.log(this.managers)
      }
    )
  }

  addManager(){

    this.managerService.addMaganer(this.manager).subscribe(
      (m: Manager) => {
        window.location.reload()
      },
      (error) => {
        alert("greska")
      },
    )
  }

}
