import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Form } from '../Form';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { faCheck } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-form-item',
  templateUrl: './form-item.component.html',
  styleUrls: ['./form-item.component.css']
})
export class FormItemComponent implements OnInit {
  @Input() form!: Form;
  @Output() onDeleteForm: EventEmitter<Form> = new EventEmitter;
  @Output() onApproveForm: EventEmitter<Form> = new EventEmitter;

  faTimes = faTimes;
  faCheck = faCheck;

  constructor() { }

  ngOnInit(): void {
  }

  onDelete(form: Form) {
    this.onDeleteForm.emit(form);
  }

  onApprove(form: Form) {
    this.onApproveForm.emit(form);
  }


  // isPending(form: Form) {
  //   if (this.form.formStatus === "pending") {
  //     return true;
  //   }
  //   else return false;
  // }

}
