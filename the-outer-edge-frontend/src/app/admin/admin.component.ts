import { Component, OnInit } from '@angular/core';
import { Form } from '../Form';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  title: string = "Admin";
  constructor() { }

  ngOnInit(): void {
  }

  isPending(form: Form) {
    if (form.formStatus === "pending") {
      return true;
    }
    else return false;
  }

}
