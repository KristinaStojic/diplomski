import { PaymentService } from './../../service/payment.service';
import { Address } from './../../model/address';
import { Client } from './../../model/client';
import { StreetViewControlOptions } from '@agm/core';
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Payment } from 'src/app/model/payment';
import Swal from 'sweetalert2';

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
  valid: Boolean = true

  constructor(private paymentService: PaymentService) { }

  ngOnInit(): void {
    this.date = this.pipe.transform(Date.now(), 'dd/MM/yyyy');
    
  }

  addPayment(){
    
    this.payment.client = this.client
    this.payment.clientAddress = this.clientAddress;
    this.payment.receiver = this.receiver;
    this.payment.receiverAddress = this.receiverAddress;
    this.isValid()

    if(this.valid){
      // this.paymentService.addPayment(this.payment).subscribe(
      //   (p: Payment) => {
      //     window.location.reload()
      //   },
      //   (error) => {
      //     Swal.fire({
      //       icon: 'error',
      //       title: 'Упс...',
      //       text: 'Дошло је до грешке!',
      //     })
      //   },
      // )
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

}
