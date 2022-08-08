import { Shipment } from 'src/app/model/shipment';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ShipmentService } from 'src/app/service/shipment.service';

@Component({
  selector: 'app-shipment',
  templateUrl: './shipment.component.html',
  styleUrls: ['./shipment.component.scss']
})
export class ShipmentComponent implements OnInit {
  
  shipments: Shipment[]

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

}
