import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-edit-personal-info',
  templateUrl: './edit-personal-info.component.html',
  styleUrls: ['./edit-personal-info.component.scss']
})
export class EditPersonalInfoComponent implements OnInit {


  form = new FormGroup({   
    oldPassword: new FormControl('', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])), 
    password: new FormControl('', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])), 
    passwordRepeated: new FormControl('', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])),  
  })
  submitted=false

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(){

  }
}
