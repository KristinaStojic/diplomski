import { ActivatedRoute } from '@angular/router';
import { AuthService } from './../../service/auth.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})

export class ChangePasswordComponent implements OnInit {

  form = new FormGroup({   
    oldPassword: new FormControl('', Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(32)])), 
    password: new FormControl('', Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(32)])), 
    passwordRepeated: new FormControl('', Validators.compose([Validators.required, Validators.minLength(1), Validators.maxLength(32)])),  
  })
  submitted=false
  notification: String = "";
 
  constructor(private authService: AuthService,
    private route: ActivatedRoute,
    ) { }

  ngOnInit(): void {
 
  }

  onSubmit(){
    var dto = {
      "email": localStorage.getItem('user'),
      "oldPassword": this.form.get('oldPassword')!.value,
      "password": this.form.get('password')!.value,
      "passwordRepeated": this.form.get('passwordRepeated')!.value
    }

    this.authService.changePassword(dto).subscribe(
      (data) => { 
        // Swal.fire({
          
        //   icon: "success",
        //   text: "Лозинка успјешно промијењена!",
          
        // })
        window.location.reload()
      },
      (err) => {  
        this.submitted = false; 
        this.form.reset()
        this.notification = 'Лозинке се не поклапају или је погрешна стара!'
      } 
    ) 

    
  }

}
