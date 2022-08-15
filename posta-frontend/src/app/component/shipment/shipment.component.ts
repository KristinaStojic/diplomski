import { Shipment } from 'src/app/model/shipment';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ShipmentService } from 'src/app/service/shipment.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-shipment',
  templateUrl: './shipment.component.html',
  styleUrls: ['./shipment.component.scss']
})
export class ShipmentComponent implements OnInit {
  
  shipments: Shipment[]
  selectedShipment: Shipment
  newShipmentStatus: String
  searchCriteria: String = ""
  shipmentCode: String = ""

  constructor(private shipmentService:ShipmentService ,private router: Router) { }

  ngOnInit(): void {
    this.shipmentService.getAllByWorker(localStorage.getItem('user')).subscribe(
      (shipments: Shipment[]) => {
        this.shipments = shipments
        console.log(this.shipments)
      }
    )
  }

  addShipment(){
    this.router.navigate(['/add-shipment']);
  }

  selectShipment(s){
    this.selectedShipment = s
    console.log(this.selectedShipment)
  }

  newStatus($event){
    var newStatus = $event.target.value
    console.log(newStatus)
    if(newStatus == 1){
      this.newShipmentStatus = 'Чека на испоруку'
    }
    else if(newStatus == 2){
      this.newShipmentStatus = 'Послато на испоруку'
    }
    else if(newStatus == 3){
      this.newShipmentStatus = 'Достављено'
    }
    else if(newStatus == 4){
      this.newShipmentStatus = 'Враћено'
    }
  }

  changeStatus(){
    //this.selectedShipment.shipmentStatus = this.newShipmentStatus
    var changedStatus = {
      "id": this.selectedShipment.id,
      "newStatus": this.newShipmentStatus,
      "email": this.selectedShipment.email,
      "code": this.selectedShipment.code,
      "emailReport": this.selectedShipment.emailReport,
      "counterWorkerEmail": localStorage.getItem('user')
    }

    console.log(this.selectedShipment.shipmentStatus)

    this.shipmentService.editShipmentStatus(changedStatus).subscribe(
      (m: Shipment) => {
        console.log(m.id)
        window.location.reload()
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Упс...',
          text: 'Дошло је до грешке!',
        })
      }
    )
  }

  getAll(){
    this.searchCriteria = ""

    this.shipmentService.getAllByWorker(localStorage.getItem('user')).subscribe(
      (shipments: Shipment[]) => {
        this.shipments = shipments
        console.log(this.shipments)
      }
    )
  }

  search(){

    var dto = {
      "code": this.searchCriteria,
      "worker": localStorage.getItem('user')
    }

    this.shipmentService.searchByCode(dto).subscribe(
      (shipments: Shipment[]) => {
        this.shipments = shipments
        console.log(this.shipments)
      }
    )
  }


  isAccountingWorker() {
    let role = localStorage.getItem("role");
    if (role == "ROLE_ACCOUNTING_WORKER"){
      return true;
    }
    return false;
  }

  isCounterWorker() {
    let role = localStorage.getItem("role");
    if (role == "ROLE_COUNTER_WORKER"){
      return true;
    }
    return false;
  }

  deliverShipment(){
    var changedStatus = {
      "id": this.selectedShipment.id,
      "newStatus": "Достављено",
      "email": this.selectedShipment.email,
      "code": this.selectedShipment.code,
      "emailReport": this.selectedShipment.emailReport,
      "counterWorkerEmail": localStorage.getItem('user')
    }

    console.log(this.selectedShipment.shipmentStatus)

    this.shipmentService.editShipmentStatus(changedStatus).subscribe(
      (m: Shipment) => {
        console.log(m.id)
        window.location.reload()
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Упс...',
          text: 'Дошло је до грешке!',
        })
      }
    )
  }


  recordShipment(){

    var dto = {
      "code": this.shipmentCode,
      "accountingWorkerEmail": localStorage.getItem('user')
    }

    this.shipmentService.recordShipmentInPostOffice(dto).subscribe(
      (p: Shipment) => {
       window.location.reload()
       //this.router.navigate(['/shipments']);

      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Упс...',
          text: 'Пошиљка са унесеном шифром не постоји у систему!',
        })
      },
    )
  }
}
