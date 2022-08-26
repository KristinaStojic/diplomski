import { ShipmentService } from './../../service/shipment.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-accounting-worker-home',
  templateUrl: './accounting-worker-home.component.html',
  styleUrls: ['./accounting-worker-home.component.scss']
})
export class AccountingWorkerHomeComponent implements OnInit {

  myChartYear: any;
  year: any;
  canvas: any;
  ctx: any;

  constructor(private router: Router, private shipmentService: ShipmentService) { }

  ngOnInit(): void {
    var dto = {
      "worker": localStorage.getItem('user'),
      "year": "2022"
    }

    this.shipmentService.getNumberofShipmentsYearly(dto).subscribe((data : any) => {
      if(this.myChartYear !== undefined){
        this.myChartYear.destroy();
      }

      this.yearlyReport(data);
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

}
