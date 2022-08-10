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
  

  constructor(private shipmentService:ShipmentService ,private router: Router) { }

  ngOnInit(): void {
    this.shipmentService.getAll().subscribe(
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
      "newStatus": this.newShipmentStatus
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

    this.shipmentService.getAll().subscribe(
      (shipments: Shipment[]) => {
        this.shipments = shipments
        console.log(this.shipments)
      }
    )
  }

  search(){
    this.shipmentService.searchByCode(this.searchCriteria).subscribe(
      (shipments: Shipment[]) => {
        this.shipments = shipments
        console.log(this.shipments)
      }
    )
  }
}
