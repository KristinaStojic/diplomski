import { PaymentService } from './../../service/payment.service';
import { AuthService } from './../../service/auth.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-personal-info',
  templateUrl: './edit-personal-info.component.html',
  styleUrls: ['./edit-personal-info.component.scss']
})
export class EditPersonalInfoComponent implements OnInit {


  form = new FormGroup({   
    name: new FormControl('', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])), 
    surname: new FormControl('', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])), 
    email: new FormControl('', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])),  
    phone: new FormControl('', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])),  

  })
  submitted=false
  edited : Boolean = false
  loggedUser: any
  constructor(private authService: AuthService) { }

  ngOnInit(): void {

    this.authService.getByEmail(localStorage.getItem('user')).subscribe(
      (u: any) => {
        this.form.get('name')?.setValue(u.name)
        this.form.get('surname')?.setValue(u.surname)
        this.form.get('email')?.setValue(u.email)
        this.form.get('phone')?.setValue(u.phoneNumber)
        console.log(u)
      }
    )

  }

  onSubmit(){

      var dto = {
        "oldEmail": localStorage.getItem('user'),
        "email":  this.form.get('email')?.value,
        "name":  this.form.get('name')?.value,
        "surname":  this.form.get('surname')?.value
      }
      console.log(dto)

      this.authService.editUser(dto).subscribe(
        (m: any) => {
          Swal.fire({
            icon: 'success',
            text: 'Uspješno izmijenjeni podaci!',
          })        
        },
        (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Ups...',
            text: 'Došlo je do greške!',
          })
        }
      )
  }

  
}
