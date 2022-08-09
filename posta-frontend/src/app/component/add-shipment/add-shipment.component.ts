import { ShipmentService } from './../../service/shipment.service';
import { Address } from './../../model/address';
import { Client } from './../../model/client';
import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Shipment } from 'src/app/model/shipment';
import { throwToolbarMixedModesError } from '@angular/material/toolbar';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

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
  valid: Boolean = true
  isNaN: Boolean = false
  totalPrice: number = 0
  weightPrice: number = 0
  typePrice: number = 0

  constructor(private shipmentService: ShipmentService,
    private router: Router,
    ) { }

  ngOnInit(): void {
    this.date = this.pipe.transform(Date.now(), 'dd/MM/yyyy');
    this.worker = localStorage.getItem('user')
    this.shipment.value = 0

  }

  addShipment(){
    //this.setShipmentFields()
    this.isValid()

    if(this.valid){

      if(this.shipment.shipmentType == 'PACKAGE'){
        this.shipment.weight = this.shipment.weight * 1000
      }

      this.shipmentService.addShipment(this.shipment).subscribe(
        (p: Shipment) => {
         //window.location.reload()
         this.router.navigate(['/shipments']);

        },
        (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Упс...',
            text: 'Дошло је до грешке!',
          })
        },
      )
      console.log(this.shipment)

    }else{
      Swal.fire({
              icon: 'error',
              title: 'Упс...',
              text: 'Попуните сва поља у исправном формату!',
            })
            console.log(this.shipment)

    }
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
    }

  }

  isValid(){
    if(this.client.name == undefined || this.client.surname == undefined){
      this.valid = false
      return
    }
    if(this.receiver.name == undefined || this.receiver.surname == undefined){
      this.valid = false;
      return
    }
    if(this.clientAddress.city == undefined || this.clientAddress.country == undefined || this.clientAddress.postalCode == undefined || this.clientAddress.street == undefined || this.clientAddress.streetNumber == undefined){
      this.valid = false;
      return
    }
    if(this.receiverAddress.city == undefined || this.receiverAddress.country == undefined || this.receiverAddress.postalCode == undefined || this.receiverAddress.street == undefined || this.receiverAddress.streetNumber == undefined){
      this.valid = false;
      return
    }

    if(this.shipment.weight == undefined || this.shipment.value == undefined){
      this.valid = false;
      return
    }

    if(isNaN(this.shipment.weight) || isNaN(this.shipment.value)){
      this.valid = false;
      this.isNaN = true;
      return
    }

    this.valid = true;

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
      this.shipment.smsReport = true
      this.sms = true
    }
    else{
      this.shipment.smsReport = false
      this.sms = false
      this.shipment.smsNumber = ''
    }
  }


  findTotalPrice(){
    console.log("cao")
    this.setShipmentFields()
    this.isValid()
    if(!this.valid){
      Swal.fire({
        icon: 'error',
        title: 'Упс...',
        text: 'Попуните сва поља у исправном формату!',
      })
    }

    this.totalPrice = 0
    
    if(this.shipment.shipmentType == 'PACKAGE'){
        this.typePrice = 100;

        this.totalPrice += 100
        if(this.shipment.weight > 1 && this.shipment.weight < 5){
          this.weightPrice = 500
          this.totalPrice += 500;
        }
        else if(this.shipment.weight >= 5 && this.shipment.weight < 15){
          this.weightPrice = 700
          this.totalPrice += 700;
        } 
        else if(this.shipment.weight >= 15){
          this.weightPrice = 1000
          this.totalPrice += 1000;
        } 
    }else{
        this.typePrice = 50

        this.totalPrice += 50
        if(this.shipment.weight < 300){
          this.weightPrice = 100
          this.totalPrice += 100;
        }
        else if(this.shipment.weight >= 300 && this.shipment.weight < 500){
          this.weightPrice = 200
          this.totalPrice += 200;
        } 
        else if(this.shipment.weight >= 500 && this.shipment.weight < 1000){
          this.weightPrice = 300
          this.totalPrice += 300;
        } 
    }

    if(this.shipment.smsReport){
      this.totalPrice += 100;
    }
    if(this.shipment.returnReceipt){
      this.totalPrice += 50;
    }

    this.shipment.totalPrice = this.totalPrice;
  }

}
