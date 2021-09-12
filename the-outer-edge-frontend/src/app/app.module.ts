import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { FormComponent } from './form/form.component';
import { FormTableComponent } from './form-table/form-table.component';
import { HttpClientModule } from '@angular/common/http';

import { AuthModule } from './auth/auth.module';
import { LoginComponent } from './authComponents/login/login.component';
import { SharedModule } from './shared/shared.module';

import { InfoComponent } from './info/info.component';
import { NavComponent } from './nav/nav.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    FormComponent,
    FormTableComponent,
    LoginComponent,
    InfoComponent,
    NavComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, 
    SharedModule,
    AuthModule
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
