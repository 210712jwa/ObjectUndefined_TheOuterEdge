import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormService } from '../form.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Form } from '../../model/form'
import { GoogleMapsModule } from '@angular/google-maps';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  zoom = 12;
  center!: google.maps.LatLngLiteral;

  options: google.maps.MapOptions = {
    mapTypeId: 'hybrid',
    zoomControl: false,
    scrollwheel: false,
    disableDoubleClickZoom: true,
    maxZoom: 15,
    minZoom: 8,
  }



  //@Input("allForm")
  allForm: Form[] = [];

  constructor(private router:Router, private formService:FormService) { }

  ngOnInit(): void {
    this.getForm();
    console.log(this.allForm);

    navigator.geolocation.getCurrentPosition((position) => {
      this.center = {
        lat: 70.7, //position.coords.latitude,
        lng: 77.8, //position.coords.longitude,
      }
    })
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