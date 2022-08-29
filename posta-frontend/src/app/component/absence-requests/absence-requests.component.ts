import { PaymentService } from './../../service/payment.service';
import { AbsenceRequestService } from './../../service/absence-request.service';
import { AbsenceRequest } from './../../model/absence-request';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-absence-requests',
  templateUrl: './absence-requests.component.html',
  styleUrls: ['./absence-requests.component.scss']
})
export class AbsenceRequestsComponent implements OnInit {

  requests: AbsenceRequest[]
  selectedRequest: AbsenceRequest

  constructor(private requestService: AbsenceRequestService) { }

  ngOnInit(): void {
    this.requestService.getAll().subscribe(
      (requests: AbsenceRequest[]) => {
        this.requests = requests
        console.log(this.requests)
      }
    )
  }

  selectRequest(r){
    this.selectedRequest = r;
  }

  approve(){

    var dto = {
      "worker": localStorage.getItem('user'),
      "id": this.selectedRequest.id,
      "approved": true
    }

    this.requestService.processRequest(dto).subscribe(
      (m: any) => {
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

  reject(){

    var dto = {
      "worker": localStorage.getItem('user'),
      "id": this.selectedRequest.id,
      "approved": false
    }

    this.requestService.processRequest(dto).subscribe(
      (m: any) => {
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

}
