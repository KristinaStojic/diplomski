import { Client } from './../../model/client';
import { Address } from './../../model/address';
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
  payoff: Payoff = new Payoff()
  client: Client = new Client()
  clientAddress : Address = new Address()
  worker: any

  constructor(private payoffService: PayoffService) { }

  ngOnInit(): void {

    this.payoff.payoffType = "PENSION"
    this.worker = localStorage.getItem('user')
    this.payoff.counterWorker = this.worker

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
    this.payoff.client = this.client;
    this.payoff.address = this.clientAddress

    console.log(this.payoff)


    this.payoffService.addPayoff(this.payoff).subscribe(
      (p: Payoff) => {
       window.location.reload()

      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Упс...',
          text: 'Дошло је до грешке!',
        })
      },
    )

  }

  selectPayoff(p){
    this.selectedPayoff = p;
  }

  payOff(){

    var dto = {
      "id": this.selectedPayoff.id,
      "worker": localStorage.getItem('user')
    }

    this.payoffService.payOff(dto).subscribe(
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

  selectPayoffType($event){
    var selected = $event.target.value

    if(selected == 1){
      this.payoff.payoffType = "PENSION"
    }else if(selected == 2){
      this.payoff.payoffType = "CHILDRENS_ALLOWANCE"
    }else if(selected == 3){
      this.payoff.payoffType = "DISABILITY_ALLOWANCE"
    }
  }
}
