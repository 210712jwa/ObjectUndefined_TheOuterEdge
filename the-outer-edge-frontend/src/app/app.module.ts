import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { FormComponent } from './form/form.component';
import { FormTableComponent } from './form-table/form-table.component';
import { HttpClientModule } from '@angular/common/http';
import { SearchComponent } from './search/search.component';
import { FilterSearchComponent } from './filter-search/filter-search.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    FormComponent,
    FormTableComponent,
    SearchComponent,
    FilterSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
