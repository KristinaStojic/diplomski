import { PaymentService } from './../../service/payment.service';
import { Address } from './../../model/address';
import { Client } from './../../model/client';
import { StreetViewControlOptions } from '@agm/core';
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Payment } from 'src/app/model/payment';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-payment',
  templateUrl: './add-payment.component.html',
  styleUrls: ['./add-payment.component.scss']
})
export class AddPaymentComponent implements OnInit {
  map = new Map();
  date: any
  pipe = new DatePipe('en-US');
  payment: Payment = new Payment()
  client: Client = new Client()
  clientAddress : Address = new Address()
  receiver: Client = new Client()
  receiverAddress: Address = new Address()
  valid: Boolean = true
  worker: any

  constructor(private paymentService: PaymentService, private router: Router) { }

  ngOnInit(): void {
    this.date = this.pipe.transform(Date.now(), 'dd/MM/yyyy');
    this.worker = localStorage.getItem('user')

    this.map.set("21000","Novi Sad")
    this.map.set("75400","Zvornik")
    this.map.set("24000","Subptica")
    this.map.set("76300","Bijeljina")
    this.map.set("20000","Dubrovnik")
    this.map.set("11000","Beograd")
  }

  addPayment(){
    
    this.payment.client = this.client
    this.payment.clientAddress = this.clientAddress;
    this.payment.receiver = this.receiver;
    this.payment.receiverAddress = this.receiverAddress;
    console.log(this.receiverAddress)
    this.payment.counterWorker = this.worker;
    this.isValid()

    if(this.valid){
      this.paymentService.addPayment(this.payment).subscribe(
        (p: Payment) => {
          this.router.navigate(['/payments']);
        },
        (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Упс...',
            text: 'Дошло је до грешке!',
          })
        },
      )
      console.log(this.payment)

    }else{
      Swal.fire({
              icon: 'error',
              title: 'Упс...',
              text: 'Попуните сва поља!',
            })
    }
    
  }

  isValid(){
    if(this.client.name == "" || this.client.surname == ""){
      this.valid = false
      return
    }
    if(this.receiver.name == "" || this.receiver.surname == ""){
      this.valid = false;
      return
    }
    if(this.clientAddress.city == "" || this.clientAddress.country == "" || this.clientAddress.postalCode == "" || this.clientAddress.street == "" || this.clientAddress.streetNumber == ""){
      this.valid = false;
      return
    }
    if(this.receiverAddress.city == "" || this.receiverAddress.country == "" || this.receiverAddress.postalCode == "" || this.receiverAddress.street == "" || this.receiverAddress.streetNumber == ""){
      this.valid = false;
      return
    }
    if(this.payment.currency == "" || this.payment.model == "" || this.payment.paymentCode == "" || this.payment.purpose == "" || this.payment.receivingPlace == "" || this.payment.referenceNumber == "" || this.payment.amount == null){
      this.valid = false;
      return
    }
    
    this.valid = true;
    
  }

  findCity(){
    this.clientAddress.city = this.map.get(this.clientAddress.postalCode)
    if(this.clientAddress.city == 'Novi Sad' || this.clientAddress.city == 'Subotica' || this.clientAddress.city == 'Beograd'){
      this.clientAddress.country = 'Srbija'
    }else if(this.clientAddress.city == 'Bijeljina' || this.clientAddress.city == 'Zvornik'){
      this.clientAddress.country = 'BIH'
    }else if(this.clientAddress.city == 'Dubrovnik'){
      this.clientAddress.country = 'Hrvatska'
    }else{
      this.clientAddress.country = ''
    }
  }

  
  findCityReceiver(){
    this.receiverAddress.city = this.map.get(this.receiverAddress.postalCode)
    if(this.receiverAddress.city == 'Novi Sad' || this.receiverAddress.city == 'Subotica' || this.receiverAddress.city == 'Beograd'){
      this.receiverAddress.country = 'Srbija'
    }else if(this.receiverAddress.city == 'Bijeljina' || this.receiverAddress.city == 'Zvornik'){
      this.receiverAddress.country = 'BIH'
    }else if(this.receiverAddress.city == 'Dubrovnik'){
      this.receiverAddress.country = 'Hrvatska'
    }else{
      this.receiverAddress.country = ''
    }
  }

}
