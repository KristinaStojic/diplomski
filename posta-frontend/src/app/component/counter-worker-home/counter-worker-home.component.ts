import { PaymentService } from './../../service/payment.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-counter-worker-home',
  templateUrl: './counter-worker-home.component.html',
  styleUrls: ['./counter-worker-home.component.scss']
})
export class CounterWorkerHomeComponent implements OnInit {

  canvas: any;
  ctx: any;
  id;
  myChart: any;

  constructor(private router: Router, private paymentService: PaymentService) { }

  ngOnInit(): void {
    this.paymentService.getNumberofPaymentsYearly().subscribe((data : any) => {

      console.log(data)
    let keys = Object.keys(data)
    let values = Object.values(data)
    this.canvas = document.getElementById('myChartYear');
    this.ctx = this.canvas.getContext('2d');
    let myChartYear = new Chart(this.ctx, {
      type: 'bar',
      data: {
          labels: keys,
          datasets: [{
              label: '# of Reservations',
              data: values,
              backgroundColor: [
                  'rgba(255, 99, 132, 1)',
                  'rgba(54, 162, 235, 1)',
                  'rgba(54, 100, 235, 1)'
              ],
              borderWidth: 1,

          }],
      },

      options: {
        responsive: false,
        display:true,

      }
    });

    })
  }

  allPayments(){
    this.router.navigate(['/payments']);

  }
}
