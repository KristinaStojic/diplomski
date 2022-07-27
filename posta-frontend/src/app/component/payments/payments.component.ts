import { PaymentService } from './../../service/payment.service';
import { Component, OnInit } from '@angular/core';
import { Payment } from 'src/app/model/payment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss']
})
export class PaymentsComponent implements OnInit {

  payments: Payment[]


  constructor(private paymentService: PaymentService, private router: Router) { }

  ngOnInit(): void {
    this.paymentService.getAll().subscribe(
      (payments: Payment[]) => {
        this.payments = payments
        console.log(this.payments)
      }
    )
  }

  addPayment(){
    this.router.navigate(['/add-payment']);
  }

}
