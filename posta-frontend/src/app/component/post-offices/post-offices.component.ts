import { ManagerService } from './../../service/manager.service';
import { Manager } from './../../model/manager';
import { PostOfficeService } from './../../service/post-office.service';
import { Component, OnInit } from '@angular/core';
import { PostOffice } from 'src/app/model/post-office';
import { MapsAPILoader, MouseEvent } from '@agm/core';

@Component({
  selector: 'app-post-offices',
  templateUrl: './post-offices.component.html',
  styleUrls: ['./post-offices.component.scss']
})
export class PostOfficesComponent implements OnInit {
  postOffices: PostOffice[]
  lat = 45.23731467405363;
  lng = 19.842083286315937;
  private geoCoder;
  address: string = '';
  zoom: number;
  longitude: number = 0;
  latitude: number = 0;
  freeManagers: Manager[]
  managerId;
  newPostOffice= {
    "street": "",
    "city": "",
    "country": "",
    "phoneNumber": "",
    "streetNumber": "",
    "postalCode": "",
    "managerId": "",
    "latitude": 0,
    "longitude": 0
  }

  constructor(private postOfficeService: PostOfficeService,
    private managerService: ManagerService) { }

  ngOnInit(): void {
    this.geoCoder = new google.maps.Geocoder();


    this.getPostOffices()

    this.getFreeManagers()
  }

  getPostOffices(){
    this.postOfficeService.getAll().subscribe(
      (post: PostOffice[]) => {
        this.postOffices = post
        console.log(this.postOffices)
      }
    )
  }
  getFreeManagers(){
    this.managerService.getFreeManagers().subscribe(
      (managers: Manager[]) => {
        this.freeManagers = managers
        console.log(this.freeManagers)
      }
    )
  }

  addPostOffice(){

    this.postOfficeService.addPostOffice(this.newPostOffice).subscribe(
      (m: PostOffice) => {
        window.location.reload()
      },
      (error) => {
        alert("greska")
      },
    )
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


  markerDragEnd($event: MouseEvent) {
    console.log($event.coords.lat)
    console.log($event.coords.lng)

    this.latitude= $event.coords.lat
    this.longitude = $event.coords.lng

    this.newPostOffice.longitude = $event.coords.lng
    this.newPostOffice.latitude = $event.coords.lat

    this.getAddress($event.coords.lat, $event.coords.lng);
  }


  getAddress(latitude, longitude) {
    this.geoCoder.geocode({ 'location': { lat: latitude, lng: longitude } }, (results, status) => {
      //console.log(results);
      //console.log(status);
      if (status === 'OK') {
        if (results[0]) {
          this.zoom = 6;
          this.address = results[0].formatted_address;
          console.log(results[0])
          if(results[0].address_components.filter(ac=>~ac.types.indexOf('locality')) != undefined){
            this.newPostOffice.city = results[0].address_components.filter(ac=>~ac.types.indexOf('locality'))[0].long_name
          }

          if(results[0].address_components.filter(ac=>~ac.types.indexOf('country')) != undefined){
            this.newPostOffice.country = results[0].address_components.filter(ac=>~ac.types.indexOf('country'))[0].long_name
          }

          if( results[0].address_components.filter(ac=>~ac.types.indexOf('street_number')) != undefined){
            // console.log(results[0].address_components.filter(ac=>~ac.types.indexOf('street_number'))[0].long_name)

            this.newPostOffice.streetNumber = results[0].address_components.filter(ac=>~ac.types.indexOf('street_number'))[0].long_name
          }

          if(results[0].address_components.filter(ac=>~ac.types.indexOf('route')) != undefined){
            this.newPostOffice.street = results[0].address_components.filter(ac=>~ac.types.indexOf('route'))[0].long_name
          }

          if(results[0].address_components.filter(ac=>~ac.types.indexOf('postal_code')) != undefined){
            this.newPostOffice.postalCode = results[0].address_components.filter(ac=>~ac.types.indexOf('postal_code'))[0].long_name
          }

        } else {
          window.alert('No results found');
        }
      } else {
        window.alert('Geocoder failed due to: ' + status);
      }

    });

    console.log(this.newPostOffice)
  }


  changeManager($event){
    var selected = $event.target.value
    var selectedName = selected.split(" ")[0]
    var selectedSurname = selected.split(" ")[1]

    console.log($event.target.value)

    for(let m of this.freeManagers){
      if(m.name === selectedName && m.surname ==selectedSurname){
        this.managerId = m.id
      } 
    }

    console.log(this.managerId)
    this.newPostOffice.managerId = this.managerId

  }

}
