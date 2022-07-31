import { Address } from './../../model/address';
import { Client } from './../../model/client';
import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Shipment } from 'src/app/model/shipment';

@Component({
  selector: 'app-add-shipment',
  templateUrl: './add-shipment.component.html',
  styleUrls: ['./add-shipment.component.scss']
})
export class AddShipmentComponent implements OnInit {

  date: any
  pipe = new DatePipe('en-US');
  client: Client = new Client()
  clientAddress : Address = new Address()
  receiver: Client = new Client()
  receiverAddress: Address = new Address()
  worker: any
  shipment: Shipment = new Shipment()

  constructor() { }

  ngOnInit(): void {
    this.date = this.pipe.transform(Date.now(), 'dd/MM/yyyy');
    this.worker = localStorage.getItem('user')
  }

}
