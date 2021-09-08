import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import{ Form } from '../model/form';

@Injectable({
  providedIn: 'root'
})
export class FormService {

  constructor(private http: HttpClient) { }

  getForms(): Observable<Form[]> {
    return this.http.get<Form[]>('http://localhost:8080/TheOuterEdge/form');
  }
}
