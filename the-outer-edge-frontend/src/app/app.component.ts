import { HttpErrorResponse } from '@angular/common/http';
import { Component, Output } from '@angular/core';
import{ Form } from '../model/form';
import { FormService } from './form.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'the-outer-edge-frontend';

  forms: Form[] = [];

  constructor(private formService: FormService) {}
  showErrorMessage = false;
  errorMessage: string = '';

  formComponentShouldBeDisplayed: boolean = true;

  ngOnInit(){
    this.getForm();
  }

  getForm(){
    this.formService.getForms().subscribe((data) => {
      this.forms = data;
      console.log(data);
    },
    (err: HttpErrorResponse) => {
      this.showErrorMessage = true;
      this.errorMessage = err.error.message;
    });
  }
}
