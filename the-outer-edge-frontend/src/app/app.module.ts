import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { FormComponent } from './form/form.component';
import { FormTableComponent } from './form-table/form-table.component';
import { HttpClientModule } from '@angular/common/http';
import { InfoComponent } from './info/info.component';
import { NavComponent } from './nav/nav.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';

import { PostDisplayComponent } from './post-display/post-display.component';

import { AdminComponent } from './admin/admin.component';
import { HeaderComponent } from './admin/header/header.component';
import { ButtonComponent } from './button/button.component';
import { FormItemComponent } from './form-item/form-item.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { PostFormComponent } from './post-form/post-form.component';



@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    FormComponent,
    FormTableComponent,
    InfoComponent,
    NavComponent,
    HomeComponent,
    LoginComponent,

    PostDisplayComponent,

    AdminComponent,
    HeaderComponent,
    ButtonComponent,
    FormItemComponent,
    PostFormComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    FontAwesomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
