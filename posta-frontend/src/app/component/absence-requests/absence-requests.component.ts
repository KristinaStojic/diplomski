import { AbsenceRequestService } from './../../service/absence-request.service';
import { AbsenceRequest } from './../../model/absence-request';
import { Component, OnInit } from '@angular/core';

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

}
