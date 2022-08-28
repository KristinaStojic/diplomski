import { Payoff } from './../../model/payoff';
import { PayoffService } from './../../service/payoff.service';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-payoffs',
  templateUrl: './payoffs.component.html',
  styleUrls: ['./payoffs.component.scss']
})
export class PayoffsComponent implements OnInit {

  searchCriteria: String = ""
  payoffs: Payoff[]
  selectedPayoff: Payoff

  constructor(private payoffService: PayoffService) { }

  ngOnInit(): void {
    this.payoffService.getAll().subscribe(
      (payoffs: Payoff[]) => {
        this.payoffs = payoffs
        console.log(this.payoffs)
      }
    )
  }

  isAccountingWorker() {
    let role = localStorage.getItem("role");
    if (role == "ROLE_ACCOUNTING_WORKER"){
      return true;
    }
    return false;
  }

  getAll(){
    this.searchCriteria = ""

    this.payoffService.getAll().subscribe(
      (payoffs: Payoff[]) => {
        this.payoffs = payoffs
        console.log(this.payoffs)
      }
    )
  }

  search(){

    var dto = {
      "criteria": this.searchCriteria,
      "worker": localStorage.getItem('user')
    }

    this.payoffService.search(dto).subscribe(
      (payoffs: Payoff[]) => {
        this.payoffs = payoffs
        console.log(this.payoffs)
      }
    )
  }

  addPayoff(){

  }

  selectPayoff(p){
    this.selectedPayoff = p;
  }

  payOff(){

    this.payoffService.payOff(this.selectedPayoff.id).subscribe(
      (m: any) => {
        window.location.reload()
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Упс...',
          text: 'Дошло је до грешке!',
        })
      }
    )
  }

}
