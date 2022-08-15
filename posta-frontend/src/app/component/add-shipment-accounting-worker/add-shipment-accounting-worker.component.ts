import { Shipment } from './../../model/shipment';
import { Address } from './../../model/address';
import { Client } from './../../model/client';
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ShipmentService } from 'src/app/service/shipment.service';

@Component({
  selector: 'app-add-shipment-accounting-worker',
  templateUrl: './add-shipment-accounting-worker.component.html',
  styleUrls: ['./add-shipment-accounting-worker.component.scss']
})
export class AddShipmentAccountingWorkerComponent implements OnInit {
  date: any
  receivingDate: any
  pipe = new DatePipe('en-US');
  client: Client = new Client()
  clientAddress : Address = new Address()
  receiver: Client = new Client()
  receiverAddress: Address = new Address()
  worker: any
  shipment: Shipment = new Shipment()
  email:Boolean = false
  letter: Boolean = true
  valueLetter: Boolean = false
  ordinaryLetter: Boolean = true
  valid: Boolean = true
  isNaN: Boolean = false
  totalPrice: number = 0
  weightPrice: number = 0
  typePrice: number = 0

  constructor(private shipmentService: ShipmentService,
    private router: Router,) { }

  ngOnInit(): void {
    this.receivingDate = this.pipe.transform(Date.now(), 'dd/MM/yyyy');
    this.worker = localStorage.getItem('user')
    this.shipment.value = 0
  }


  recordShipment(){

  }

  returnReceipt($event){
    if($event.target.checked === true){
      this.shipment.returnReceipt = true
    }
    else{
      this.shipment.returnReceipt = false
    }
  }

  personalDelivery($event){
    if($event.target.checked === true){
      this.shipment.personalDelivery = true
    }
    else{
      this.shipment.personalDelivery = false
    }
  }

  emailReport($event){
    if($event.target.checked === true){
      this.shipment.emailReport = true
      this.email = true
    }
    else{
      this.shipment.emailReport = false
      this.email = false
      this.shipment.email = ''
    }
  }

  selectLetterType($event){
    var selected = $event.target.value
    
    if(selected == 3){
      this.valueLetter = true
      this.ordinaryLetter = false
    }
    else if(selected == 1){
      this.ordinaryLetter = true
      this.valueLetter = false
      this.shipment.value = 0
    }
    else{
      this.valueLetter = false
      this.ordinaryLetter = false
      this.shipment.value = 0
    }
  }


  selectShipmentType($event){
    var selected = $event.target.value

    if(selected == 2){
      this.letter = false
      this.ordinaryLetter = false
      this.valueLetter = false
      
    }
    else{
      this.letter = true
      this.ordinaryLetter = true
    }

  }

  selectDate($event){
    console.log($event.target.value)
  }

}
