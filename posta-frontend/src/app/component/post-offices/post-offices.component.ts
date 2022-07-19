import { PostOfficeService } from './../../service/post-office.service';
import { Component, OnInit } from '@angular/core';
import { PostOffice } from 'src/app/model/post-office';

@Component({
  selector: 'app-post-offices',
  templateUrl: './post-offices.component.html',
  styleUrls: ['./post-offices.component.scss']
})
export class PostOfficesComponent implements OnInit {
  postOffices: PostOffice[]

  constructor(private postOfficeService: PostOfficeService) { }

  ngOnInit(): void {
    this.postOfficeService.getAll().subscribe(
      (post: PostOffice[]) => {
        this.postOffices = post
        console.log(this.postOffices)
      }
    )
  }

}
