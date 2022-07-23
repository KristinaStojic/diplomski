import { TokenInterceptor } from './interceptor/TokenInterceptor';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './component/login/login.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {AngularMaterialModule} from './angular-material/angular-material.module'; 
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminHomeComponent } from './component/admin-home/admin-home.component';
import { HeaderComponent } from './component/header/header.component';
import { SidebarComponent } from './component/sidebar/sidebar.component';
import { AgmCoreModule } from '@agm/core';
import { PostOfficesComponent } from './component/post-offices/post-offices.component';
import { ManagerHomeComponent } from './component/manager-home/manager-home.component';
import { NotificationComponent } from './component/notification/notification.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminHomeComponent,
    HeaderComponent,
    SidebarComponent,
    PostOfficesComponent,
    ManagerHomeComponent,
    NotificationComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NoopAnimationsModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    FormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCX5DQFPxHlQlEeFkkWzTJ41PU6FehGzVs'   // za google maps
  }),
    

  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }, 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
