import { Address } from './../../model/address';
import { Client } from './../../model/client';
import { StreetViewControlOptions } from '@agm/core';
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Payment } from 'src/app/model/payment';

@Component({
  selector: 'app-add-payment',
  templateUrl: './add-payment.component.html',
  styleUrls: ['./add-payment.component.scss']
})
export class AddPaymentComponent implements OnInit {

  date: any
  pipe = new DatePipe('en-US');
  payment: Payment = new Payment()
  client: Client = new Client()
  clientAddress : Address = new Address()
  receiver: Client = new Client()
  receiverAddress: Address = new Address()

  constructor() { }

  ngOnInit(): void {
    this.date = this.pipe.transform(Date.now(), 'dd/MM/yyyy');
  }

  addPayment(){
    
    this.payment.client = this.client
    this.payment.clientAddress = this.clientAddress;
    this.payment.receiver = this.receiver;
    this.payment.receiverAddress = this.receiverAddress;

    console.log(this.payment)
  }

}
