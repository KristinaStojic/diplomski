import { AddShipmentComponent } from './component/add-shipment/add-shipment.component';
import { ShipmentComponent } from './component/shipment/shipment.component';
import { AddPaymentComponent } from './component/add-payment/add-payment.component';
import { PaymentsComponent } from './component/payments/payments.component';
import { CounterWorkerHomeComponent } from './component/counter-worker-home/counter-worker-home.component';
import { AccountingWorkerHomeComponent } from './component/accounting-worker-home/accounting-worker-home.component';
import { NotificationComponent } from './component/notification/notification.component';
import { ManagerHomeComponent } from './component/manager-home/manager-home.component';
import { PostOfficesComponent } from './component/post-offices/post-offices.component';
import { AdminHomeComponent } from './component/admin-home/admin-home.component';
import { LoginComponent } from './component/login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    pathMatch: 'full', 
  },
  {
    path: 'admin-home',
    component: AdminHomeComponent,
  },
  {
    path: 'post-offices',
    component: PostOfficesComponent,
  },
  {
    path: 'manager-home',
    component: ManagerHomeComponent,
  },
  {
    path: 'notification',
    component: NotificationComponent,
  },
  {
    path: 'accounting-worker-home',
    component: AccountingWorkerHomeComponent,
  },
  {
    path: 'counter-worker-home',
    component: CounterWorkerHomeComponent,
  },
  {
    path: 'payments',
    component: PaymentsComponent,
  },
  {
    path: 'add-payment',
    component: AddPaymentComponent,
  },
  {
    path: 'shipments',
    component: ShipmentComponent,
  },
  {
    path: 'add-shipment',
    component: AddShipmentComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
