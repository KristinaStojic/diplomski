import { AbsenceRequest } from './../../model/absence-request';
import { AbsenceRequestService } from './../../service/absence-request.service';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-personal-absence-requests',
  templateUrl: './personal-absence-requests.component.html',
  styleUrls: ['./personal-absence-requests.component.scss']
})
export class PersonalAbsenceRequestsComponent implements OnInit {

  requests: AbsenceRequest[]
  content: String = ''

  constructor(private requestService: AbsenceRequestService) { }

  ngOnInit(): void {
    this.requestService.getAllByWorker().subscribe(
      (requests: AbsenceRequest[]) => {
        this.requests = requests
        console.log(this.requests)
      }
    )
  }

  addRequest(){

    var dto = {
      "content": this.content,
      "worker": localStorage.getItem('user')
    }

    this.requestService.addAbsenceRequest(dto).subscribe(
      (m: AbsenceRequest) => {
        window.location.reload()
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Ups...',
          text: 'Došlo je do greške!', 
        })
      },
    )
  }

}
