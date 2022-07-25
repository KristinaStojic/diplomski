import { PaymentService } from './../../service/payment.service';
import { Component, OnInit } from '@angular/core';
import { Payment } from 'src/app/model/payment';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss']
})
export class PaymentsComponent implements OnInit {

  payments: Payment[]


  constructor(private paymentService: PaymentService) { }

  ngOnInit(): void {
    this.paymentService.getAll().subscribe(
      (payments: Payment[]) => {
        this.payments = payments
        console.log(this.payments)
      }
    )
  }

}
