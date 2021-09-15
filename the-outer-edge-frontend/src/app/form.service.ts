import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import{ Form } from '../model/form';
import { Users } from '../model/user';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';


const HttpUploadOptions = {
  headers: new HttpHeaders({ "Content-Type": "multipart/form-data" })
}


@Injectable({
  providedIn: 'root'
})
export class FormService {




  constructor(private hc: HttpClient) { }

  getForms(): Observable<Form[]> {
    return this.hc.get<Form[]>(`http://localhost:8080/TheOuterEdge/form`);
  }


  post(form:Object, user:Users): Observable<Form> {
    let header = new HttpHeaders();
    header.append('Content-Type', 'application/json');
    return this.hc.post<Form>(`http://localhost:8080/TheOuterEdge/user/${user.id}/form`, 
      JSON.stringify(form),
      {
      withCredentials: true,
      headers: header
    });
  }

  postImage(formData:FormData, user:Users, formId:number): Observable<Form> {
   // header2.append('Content-Type', 'application/json');
    return this.hc.patch<Form>(`http://localhost:8080/TheOuterEdge/user/${user.id}/form/${formId}/upload`, {
      formData,
      withCredentials: true,
      header: new HttpHeaders({ 'Content-Type': 'multipart/form-data' })
    });

  }
}
