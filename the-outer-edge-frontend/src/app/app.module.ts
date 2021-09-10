import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { FormComponent } from './form/form.component';
import { FormTableComponent } from './form-table/form-table.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthModule } from './auth/auth.module';
<<<<<<< HEAD
import { LoginComponent } from './authComponents/login/login.component';
=======
import { SharedModule } from './shared/shared.module';
>>>>>>> ff82ab9b (Add shared module, auth module)

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    FormComponent,
    FormTableComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, 
<<<<<<< HEAD
=======
    SharedModule,
>>>>>>> ff82ab9b (Add shared module, auth module)
    AuthModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
