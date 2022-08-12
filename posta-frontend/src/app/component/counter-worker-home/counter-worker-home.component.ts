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
  year

  constructor(private router: Router, private paymentService: PaymentService) { }

  ngOnInit(): void {
    this.year = "2022"
    this.reportPerMonth(this.year)

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
              label: 'број уплата',
              data: values,
              backgroundColor: [
                  'rgba(253, 99, 132, 1)',
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

  selectYear($event){
    console.log($event.target.value)
    if(this.myChart != null){
      this.myChart.destroy();
    }
    this.year = $event.target.value
    this.reportPerMonth(this.year);

  }


  reportPerMonth(year){
    this.paymentService.getNumberofPaymentsMonthly(year).subscribe((data : any) => {
     

    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');
    //this.myChart.destroy();
    this.myChart = new Chart(this.ctx, {
      type: 'bar',
      data: {
          labels: ["January", "February", "March", "April", "May", "Jun", "July", "August", "September", "October", "November", "December"],
          datasets: [{
              label: '# of Reservations',
              data: [data["JANUARY"] , data["FEBRUARY"],data["MARCH"], data["APRIL"], data["MAY"], data["JUN"], data["JULY"], data["AUGUST"], data["SEPTEMBER"], data["OCTOBER"], data["NOVEMBER"], data["DECEMBER"]],
              backgroundColor: [
                  'rgba(255, 99, 132, 1)',
                  'rgba(54, 162, 235, 1)',
                  'rgba(255, 206, 86, 1)',
                  'rgba(255, 145, 86, 1)',
                  'rgba(255, 104, 86, 1)',
                  'rgba(255, 125, 86, 1)',
                  'rgba(255, 23, 86, 1)',
                  'rgba(255, 206, 75, 1)',
                  'rgba(255, 206, 124, 1)',
                  'rgba(47, 206, 124, 1)',
                  'rgba(125, 206, 124, 1)',
                  'rgba(10, 206, 124, 1)',


              ],
              borderWidth: 1
          }]
      },
      options: {
        responsive: false,
        display:true
      }
    });

      });

    
  }
}
