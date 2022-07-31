import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shipment',
  templateUrl: './shipment.component.html',
  styleUrls: ['./shipment.component.scss']
})
export class ShipmentComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  addShipment(){
    this.router.navigate(['/add-shipment']);
  }

}
