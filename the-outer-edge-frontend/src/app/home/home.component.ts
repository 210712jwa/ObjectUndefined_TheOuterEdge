import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormService } from '../form.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Form } from '../../model/form'

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  //@Input("allForm")
  allForm: Form[] = [];

  constructor(private router:Router, private formService:FormService) { }

  ngOnInit(): void {
    this.getForm();
    console.log(this.allForm);
  }


  login(){
    this.router.navigate(['login'])
  }

  print(){
    console.log(this.allForm);
  }

  getForm(){
    this.formService.getForms().subscribe((data) => {
      this.allForm = data;
      console.log(this.allForm);
    },
    (err: HttpErrorResponse) => {
      //this.showErrorMessage = true;
      //this.errorMessage = err.error.message;
    });
  }

}
