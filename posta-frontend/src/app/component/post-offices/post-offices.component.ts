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

  addPostOffice(){

    // this.managerService.addMaganer(this.manager).subscribe(
    //   (m: Manager) => {
    //     window.location.reload()
    //   },
    //   (error) => {
    //     alert("greska")
    //   },
    // )
  }

  selectPostOffice(manager){
    // this.selectedManager = manager
  }

  editPostOffice(){

    // this.managerService.editManager(this.selectedManager).subscribe(
    //   (m: Manager) => {
    //     console.log(this.selectedManager.id)
    //   },
    //   (error) => {
    //     alert("greska")
    //   }
    // )
  }

  deletePostOffice(id) {
    this.postOfficeService.delete(id).subscribe(
      (boolean:any) =>{
        window.location.reload()
      },
      (error) => {
        alert("greska")
      }
    )
  }

}
