import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import{ Users } from '../../model/user'
import { Router } from '@angular/router'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = '';
  password: string = '';

  showErrorMessage: boolean = false;
  errorMessage: string = '';

  constructor(private ls:LoginService, private router:Router) { }

  ngOnInit(): void {
    this.checkIfAlreadyLoggedIn();
    
  }

  login() {
    console.log(this.username);
    console.log(this.password);
    this.ls.login(this.username, this.password).subscribe ((data: Users) => {
      this.router.navigate(['']);
    },
    (err: HttpErrorResponse) => {
      this.showErrorMessage = true;
      this.errorMessage = err.error.message;
  });

  }

  checkIfAlreadyLoggedIn() {
    this.ls.checkIfLoggedIn().subscribe((data) => {
      this.router.navigate(['']);
    });

  }
}
