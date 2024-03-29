import { Employee } from './../../model/employee';
import { EmployeeService } from './../../service/employee.service';
import { ManagerService } from './../../service/manager.service';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-manager-home',
  templateUrl: './manager-home.component.html',
  styleUrls: ['./manager-home.component.scss']
})
export class ManagerHomeComponent implements OnInit {

  employee= {
    "name": "",
    "surname": "",
    "phoneNumber": "",
    "email": "",
    "role": "",
    "managerEmail": localStorage.getItem('user')
  }

  employees: Employee[]
  selectedEmployee: Employee
  
  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {

    this.employeeService.getAllByManagersPostOffice(localStorage.getItem('user')).subscribe(
      (employee: Employee[]) => {
        this.employees = employee
        console.log(this.employees)
      }
    )
  }

  addEmployee(){
    this.employeeService.addEmployee(this.employee).subscribe(
      (m: Employee) => {
        window.location.reload()
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Упс...',
          text: 'Дошло је до грешке!',
        })
      },
    )
  }

  selectRole($event){
    var selected = $event.target.value
    if(selected == 'Poštar'){
      this.employee.role = 'ROLE_POSTMAN'
    }
    else if(selected == 'Šalterski radnik'){
      this.employee.role = 'ROLE_COUNTER_WORKER'
    }
    else if(selected == 'Obračunski radnik'){
      this.employee.role = 'ROLE_ACCOUNTING_WORKER'
    }

    console.log(this.employee)
  }

  selectManager(id){

    this.employeeService.getById(id).subscribe(
      (employee: Employee) => {
        this.selectedEmployee = employee
        console.log(this.employees)
      }
    )
  }

  deleteManager(id){
    this.employeeService.delete(id).subscribe(
      (boolean:any) =>{
        window.location.reload()
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Упс...',
          text: 'Дошло је до грешке!',
        })
      }
    )
  }

}
