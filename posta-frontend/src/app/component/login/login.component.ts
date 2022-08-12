import { AuthService } from './../../service/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

interface DisplayMessage {
  msgType: string;
  msgBody: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  notification: DisplayMessage;
  submitted = false;
  role: any

  constructor(private _http:HttpClient,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.form = this.formBuilder.group({
      email: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])]
    });
  }


  onSubmit() {

    this.submitted = true;
    this.authService.login(this.form.value)
      .subscribe(data => {
        console.log(data)
        this.role = localStorage.getItem("role");

        switch(this.role){
          case "ROLE_ADMIN":
            this.router.navigate(['/admin-home']);
            break;
          case "ROLE_COUNTER_WORKER":
            this.router.navigate(['/counter-worker-home']);
            break;
          case "ROLE_ACCOUNTING_WORKER":
            this.router.navigate(['/accounting-worker-home']);
            break;
          case "ROLE_MANAGER":
            this.router.navigate(['/manager-home']);
            break;
          }
          
           
        },
        error => {
          this.submitted = false;
          this.notification = {msgType: 'error', msgBody: 'Погрешан емаил или лозинка!'};
        });
  }
}
