import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
<<<<<<< HEAD


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AuthRoutingModule
=======
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ResetPasswordComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule
  ], 
  exports: [
    LoginComponent,
    RegisterComponent,
    ResetPasswordComponent
>>>>>>> ff82ab9b (Add shared module, auth module)
  ]
})
export class AuthModule { }
