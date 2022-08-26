import { ShipmentService } from './../../service/shipment.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Chart from 'chart.js/auto';
import Swal from 'sweetalert2';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-accounting-worker-home',
  templateUrl: './accounting-worker-home.component.html',
  styleUrls: ['./accounting-worker-home.component.scss']
})
export class AccountingWorkerHomeComponent implements OnInit {

  myChartYear: any;
  myChartMonth: any;
  myChartWeek: any;
  year: any;
  month: any;
  canvas: any;
  ctx: any;
  startDate: String | any;
  endDate: String | any;

  constructor(private router: Router, private shipmentService: ShipmentService) { }

  ngOnInit(): void {
    this.year = "2022"
    this.month = "JANUARY"
    var dto = {
      "worker": localStorage.getItem('user'),
      "year": this.year
    }

    this.shipmentService.getNumberofShipmentsYearly(dto).subscribe((data : any) => {
      if(this.myChartYear !== undefined){
        this.myChartYear.destroy();
      }

      this.yearlyReport(data);
    })


    var dtoM = {
      "worker": localStorage.getItem('user'),
      "year": this.year,
      "month": this.month
    }

    this.shipmentService.getNumberofShipmentsMonthly(dtoM).subscribe((data : any) => {
      if(this.myChartMonth !== undefined){
        this.myChartMonth.destroy();
      }

      this.monthlyReport(data);
    })
  }

  allShipments(){
    this.router.navigate(['/shipments']);
  }

  selectYear($event){

    if(this.myChartYear !== undefined){
      this.myChartYear.destroy();
    }
    this.year = $event.target.value

    var dto = {
      "worker": localStorage.getItem('user'),
      "year": this.year
    }
    this.shipmentService.getNumberofShipmentsYearly(dto).subscribe((data : any) => {
      if(this.myChartYear !== undefined){
        this.myChartYear.destroy();
      }

      this.yearlyReport(data);
    })


    var dtoM = {
      "worker": localStorage.getItem('user'),
      "year": this.year,
      "month": this.month
    }

    this.shipmentService.getNumberofShipmentsMonthly(dtoM).subscribe((data : any) => {
      if(this.myChartMonth !== undefined){
        this.myChartMonth.destroy();
      }

      this.monthlyReport(data);
    })

    
  }

  selectMonth($event){
    if(this.myChartMonth !== undefined){
      this.myChartMonth.destroy();
    }

    var month = $event.target.value

    if(month == 1){
      this.month = "JANUARY"
    }else if(month == 2){
      this.month = "FEBRUARY"
    }else if(month == 3){
      this.month = "MARCH"
    }else if(month == 4){
      this.month = "APRIL"
    }else if(month == 5){
      this.month = "MAY"
    }else if(month == 6){
      this.month = "JUN"
    }else if(month == 7){
      this.month = "JULY"
    }else if(month == 8){
      this.month = "AVGUST"
    }else if(month == 9){
      this.month = "SEPTEMBER"
    }else if(month == 10){
      this.month = "OCTOBER"
    }else if(month == 11){
      this.month = "NOVEMBER"
    }else if(month == 12){
      this.month = "DECEMBER"
    }

    var dto = {
      "worker": localStorage.getItem('user'),
      "year": this.year,
      "month": this.month
    }

    this.shipmentService.getNumberofShipmentsMonthly(dto).subscribe((data : any) => {
      if(this.myChartMonth !== undefined){
        this.myChartMonth.destroy();
      }

      this.monthlyReport(data);
    })
    
  }

  yearlyReport(data){
    
    console.log(data)
    let keys = Object.keys(data)
    let values = Object.values(data)
    this.canvas = document.getElementById('myChartYear');
    this.ctx = this.canvas.getContext('2d');
    this.myChartYear = new Chart(this.ctx, {
      type: 'pie',
      data: {
          labels: keys,
          datasets: [{
              label: 'број пошиљака',
              data: values,
              backgroundColor: [
                  'rgba(253, 12, 15, 1)',
                  'rgba(54, 162, 235, 1)',
                  'rgba(142, 21, 210, 1)',
                  'rgba(57, 95, 45, 1)'

              ],
              borderWidth: 1,

          }],
      },

      options: {
        responsive: false,
        display:true,

      }
    });

    
  }



  monthlyReport(data){
    
    console.log(data)
    let keys = Object.keys(data)
    let values = Object.values(data)
    this.canvas = document.getElementById('myChartMonth');
    this.ctx = this.canvas.getContext('2d');
    this.myChartMonth = new Chart(this.ctx, {
      type: 'pie',
      data: {
          labels: keys,
          datasets: [{
              label: 'број пошиљака',
              data: values,
              backgroundColor: [
                  'rgba(253, 12, 15, 1)',
                  'rgba(54, 162, 235, 1)',
                  'rgba(142, 21, 210, 1)',
                  'rgba(57, 95, 45, 1)'

              ],
              borderWidth: 1,

          }],
      },

      options: {
        responsive: false,
        display:true,

      }
    });

    
  }



  selectDays(){

    if(this.startDate == undefined || this.endDate == undefined){
      Swal.fire({
        icon: 'error',
        title: 'Упс...',
        text: 'Изаберите датуме!',
      }) 
    }
    else{
      var start = formatDate(this.startDate,'dd-MM-yyyy','en_US');
      var end  = formatDate(this.endDate,'dd-MM-yyyy','en_US');
  
     
  
      if(this.startDate.getTime() >= this.endDate.getTime()){
        Swal.fire({
          icon: 'error',
          title: 'Упс...',
          text: 'Почетни датум не смије бити већи или једнак крајњем!',
        }) 
      }
      else{
        start = start + " 00:00"
        end = end + " 00:00"
    
        var dto = {
          "startDate": start,
          "endDate": end,
          "worker": localStorage.getItem('user')
        }
  
        this.shipmentService.getNumberofShipmentsSelectedPeriod(dto).subscribe((data : any) => {
  
          console.log(data)
          if(this.myChartWeek !== undefined){
            this.myChartWeek.destroy();
          }
          this.reportForSelectedPeriod(data)
        })
      }
  
    }
    
  }

  reportForSelectedPeriod(data){
    let keys = Object.keys(data)
    let values = Object.values(data)
    this.canvas = document.getElementById('myChartWeek');
    this.ctx = this.canvas.getContext('2d');
    this.myChartWeek = new Chart(this.ctx, {
      type: 'pie',
      data: {
          labels: keys,
          datasets: [{
              label: 'број пошиљака',
              data: values,
              backgroundColor: [
                  'rgba(253, 12, 15, 1)',
                  'rgba(54, 162, 235, 1)',
                  'rgba(142, 21, 210, 1)',
                  'rgba(57, 95, 45, 1)'

              ],
              borderWidth: 1,

          }],
      },

      options: {
        responsive: false,
        display:true,

      }
    });
  }


}
