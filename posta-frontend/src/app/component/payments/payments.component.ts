import { Payment } from './../../model/payment';
import { PaymentService } from './../../service/payment.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss']
})
export class PaymentsComponent implements OnInit {

  payments: Payment[]
  selectedPayment: Payment

  constructor(private paymentService: PaymentService, private router: Router) { }

  ngOnInit(): void {
    this.paymentService.getAll(localStorage.getItem('user')).subscribe(
      (payments: Payment[]) => {
        this.payments = payments
        console.log(this.payments)
      }
    )
  }

  addPayment(){
    this.router.navigate(['/add-payment']);
  }

  selectPayment(p){
    this.selectedPayment = p
  }

}
