import { AuthService } from './../../service/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }



  logout() {
    this.authService.logout();
  }

  isLogged(){
    if(localStorage.getItem('user') === null){
      return false;
    }else{
      return true;
    }
  }

  isAdmin() {
    let role = localStorage.getItem("role");
    if (role == "ROLE_ADMIN" && this.isLogged){
      return true;
    }
    return false;
  }

  isManager() {
    let role = localStorage.getItem("role");
    if (role == "ROLE_MANAGER" && this.isLogged){
      return true;
    }
    return false;
  }
}
