import { Component, OnInit } from '@angular/core';
import { FormApprovalService } from '../services/form-approval.service';
import { Form } from '../Form';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  forms: Form[] = [];

  constructor(private formApprovalService: FormApprovalService) { }

  ngOnInit(): void {
    this.formApprovalService.getForms().subscribe((forms) => (this.forms = forms));
  }

  deleteForm(form: Form) {
    this.formApprovalService
    .deleteForm(form)
    .subscribe(
      () => (this.forms = this.forms.filter(f => f.id !== form.id)));
  }

  approveForm(form: Form) {
    form.formStatus = "approved";

    this.formApprovalService.updateFormApproved(form).subscribe(
      () => (this.forms)
    )
  }


}
