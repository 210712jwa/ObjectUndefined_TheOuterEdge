import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Form } from '../Form';
import { faTimes } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-form-item',
  templateUrl: './form-item.component.html',
  styleUrls: ['./form-item.component.css']
})
export class FormItemComponent implements OnInit {
  @Input() form!: Form;
  @Output() onDeleteForm: EventEmitter<Form> = new EventEmitter;
  faTimes = faTimes;

  constructor() { }

  ngOnInit(): void {
  }

  onDelete(form: Form) {
    this.onDeleteForm.emit(form);
  }

}
