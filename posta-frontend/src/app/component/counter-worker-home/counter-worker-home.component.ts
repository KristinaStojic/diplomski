import { PaymentService } from './../../service/payment.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Chart from 'chart.js/auto';
import Swal from 'sweetalert2';
import { formatDate } from '@angular/common';

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
  myChartWeek: any
  myChartWeekAmount: any
  year;
  startDate: String | any;
  endDate: String | any;
  startDateAmount: String | any;
  endDateAmount: String | any;
  myVar : Record<string, number> = {

  }

  constructor(private router: Router, private paymentService: PaymentService) { }

  ngOnInit(): void {
    this.year = "2022"
    this.reportPerMonth(this.year)

    this.paymentService.getNumberofPaymentsYearly(localStorage.getItem('user')).subscribe((data : any) => {

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
              label: 'broj uplata',
              data: values,
              backgroundColor: [
                'rgba(54, 162, 235, 1)',
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
    var dto={
      "year": year,
      "worker": localStorage.getItem('user')
    }

    this.paymentService.getNumberofPaymentsMonthly(dto).subscribe((data : any) => {
     

    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');
    this.myChart = new Chart(this.ctx, {
      type: 'bar',
      data: {
          labels: ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"],
          datasets: [{
              label: 'broj uplata',
              data: [data["JANUARY"] , data["FEBRUARY"],data["MARCH"], data["APRIL"], data["MAY"], data["JUN"], data["JULY"], data["AUGUST"], data["SEPTEMBER"], data["OCTOBER"], data["NOVEMBER"], data["DECEMBER"]],
              backgroundColor: [
                  'rgba(54, 162, 235, 1)',
                 


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



  selectDays(){

    if(this.startDate == undefined || this.endDate == undefined){
      Swal.fire({
        icon: 'error',
        title: 'Ups...',
        text: 'Izaberite datume!',
      }) 
    }
    else{
      var start = formatDate(this.startDate,'dd-MM-yyyy','en_US');
      var end  = formatDate(this.endDate,'dd-MM-yyyy','en_US');
  
     
  
      if(this.startDate.getTime() >= this.endDate.getTime()){
        Swal.fire({
          icon: 'error',
          title: 'Ups...',
          text: 'Početni datum ne smije biti veći ili jednak krajnjem!',
        }) 
      }
      else{
        start = start + " 00:00"
        end = end + " 00:00"
    
        var dto = {
          "id": this.id,
          "startDate": start,
          "endDate": end,
          "worker": localStorage.getItem('user')
        }
  
        this.paymentService.getNumberofPaymentsWeekly(dto).subscribe((data : any) => {
  
          console.log(data)
          if(this.myChartWeek !== undefined){
            this.myChartWeek.destroy();
          }
          this.reportPerWeek(data)
        })
      }
  
    }
    
  }


  reportPerWeek(data){
    this.myVar = data;
    let first = Object.keys(data)
    let values = Object.values(data)
    this.canvas = document.getElementById('myChartWeek');
    this.ctx = this.canvas.getContext('2d');
    //this.myChart.destroy();
    this.myChartWeek = new Chart(this.ctx, {
      type: 'bar',
      data: {
          labels: first,
          datasets: [{
              label: 'broj uplata',
              data: values,
              backgroundColor: 
              'rgba(54, 162, 235, 1)',
              
              borderWidth: 1
          }]
      },
      options: {
        responsive: false,
        display:true
      }
    });
  }



  selectDaysAmount(){

    if(this.startDateAmount == undefined || this.endDateAmount == undefined){
      Swal.fire({
        icon: 'error',
        title: 'Ups...',
        text: 'Izaberite datume!',
      }) 
    }
    else{
      var start = formatDate(this.startDateAmount,'dd-MM-yyyy','en_US');
      var end  = formatDate(this.endDateAmount,'dd-MM-yyyy','en_US');
  
     
  
      if(this.startDateAmount.getTime() >= this.endDateAmount.getTime()){
        Swal.fire({
          icon: 'error',
          title: 'Ups...',
          text: 'Početni datum ne smije biti veći ili jednak krajnjem!',
        }) 
      }
      else{
        start = start + " 00:00"
        end = end + " 00:00"
    
        var dto = {
          "id": this.id,
          "startDate": start,
          "endDate": end,
          "worker": localStorage.getItem('user')
        }
  
        this.paymentService.getAmountofPaymentsWeekly(dto).subscribe((data : any) => {
  
          console.log(data)
          if(this.myChartWeekAmount !== undefined){
            this.myChartWeekAmount.destroy();
          }
          this.reportPerWeekAmount(data)
        })
      }
  
    }
    
  }



  reportPerWeekAmount(data){
    this.myVar = data;
    let first = Object.keys(data)
    let values = Object.values(data)
    this.canvas = document.getElementById('myChartWeekAmount');
    this.ctx = this.canvas.getContext('2d');
    //this.myChart.destroy();
    this.myChartWeekAmount = new Chart(this.ctx, {
      type: 'bar',
      data: {
          labels: first,
          datasets: [{
              label: 'uplaćeni novac',
              data: values,
              backgroundColor: 
              'rgba(54, 162, 235, 1)',
              
              borderWidth: 1
          }]
      },
      options: {
        responsive: false,
        display:true,
      }
    });
  }
}
