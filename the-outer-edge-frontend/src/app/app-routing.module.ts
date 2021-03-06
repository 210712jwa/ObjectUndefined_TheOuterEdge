import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { PostDisplayComponent } from './post-display/post-display.component';
import { PostFormComponent } from './post-form/post-form.component';

const routes: Routes = [
  {path: '', component:HomeComponent},

  {path: 'login', component:LoginComponent},

  {path: 'post', component:PostFormComponent},

  {path: 'admin', component:AdminComponent},


  {path: 'postDisplay', component:PostDisplayComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { } export const
RoutingComponent = [HomeComponent,LoginComponent,PostDisplayComponent];
