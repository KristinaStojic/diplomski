import { Address } from './../../model/address';
import { Client } from './../../model/client';
import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Shipment } from 'src/app/model/shipment';
import { throwToolbarMixedModesError } from '@angular/material/toolbar';

@Component({
  selector: 'app-add-shipment',
  templateUrl: './add-shipment.component.html',
  styleUrls: ['./add-shipment.component.scss']
})
export class AddShipmentComponent implements OnInit {

  date: any
  pipe = new DatePipe('en-US');
  client: Client = new Client()
  clientAddress : Address = new Address()
  receiver: Client = new Client()
  receiverAddress: Address = new Address()
  worker: any
  shipment: Shipment = new Shipment()
  sms:Boolean = false
  letter: Boolean = true
  valueLetter: Boolean = false
  ordinaryLetter: Boolean = true
  
  constructor() { }

  ngOnInit(): void {
    this.date = this.pipe.transform(Date.now(), 'dd/MM/yyyy');
    this.worker = localStorage.getItem('user')
    this.shipment.value = 0

  }

  addShipment(){
    this.setShipmentFields()
    console.log(this.shipment)
  }

  setShipmentFields(){
    this.shipment.client = this.client
    this.shipment.clientAddress = this.clientAddress
    this.shipment.receiver = this.receiver
    this.shipment.receiverAddress = this.receiverAddress
    this.shipment.date = this.date
    this.shipment.counterWorker = this.worker

    if(this.letter){
      this.shipment.shipmentType = 'LETTER'

      if(this.valueLetter){
        this.shipment.letterType = 'VALUE'
      }
      else if(this.ordinaryLetter){
        this.shipment.letterType = 'ORDINARY'
      }
      else{
        this.shipment.letterType = 'REGISTERED'
      }
    }
    else{
      this.shipment.shipmentType = 'PACKAGE'
      this.shipment.letterType = ''
      this.shipment.weight = this.shipment.weight * 1000
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

  SMSReport($event){
    if($event.target.checked === true){
      this.shipment.SMSreport = true
      this.sms = true
    }
    else{
      this.shipment.SMSreport = false
      this.sms = false
      this.shipment.smsNumber = ''
    }
  }

}
