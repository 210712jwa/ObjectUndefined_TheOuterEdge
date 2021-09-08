import { Component, Input, OnInit } from '@angular/core';
import { Form } from '../../model/form'
@Component({
  selector: 'app-form-table',
  templateUrl: './form-table.component.html',
  styleUrls: ['./form-table.component.css']
})
export class FormTableComponent implements OnInit {

  @Input("allForm")
  allForm: Form[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
