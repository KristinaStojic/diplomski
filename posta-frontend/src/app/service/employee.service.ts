import { Employee } from './../model/employee';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private employee_url = 'https://localhost:8080/api/worker';


  constructor( private router: Router,
    private _http:HttpClient) { }

  public getAllByManagersPostOffice(manager): Observable<Employee[]>{
    return this._http.get<Employee[]>(`${this.employee_url}/getAllByManagersPostOffice/` + manager)
  }  

  public addEmployee(employee): Observable<Employee> {
    console.log(employee)
    return this._http.post<Employee>(`${this.employee_url}/addWorker`, employee)
  }

}
