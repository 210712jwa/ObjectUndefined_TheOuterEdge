import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Users } from '../model/user';
import { environment } from 'src/environments/environment';
import { URLSearchParams } from 'url';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private hc:HttpClient) { }
   /**
    * NOTE: backendURL is set to 4200
    * May need to change in environment.ts
    */
  login(username: string, password: string): Observable<Users> {
    return this.hc.post<Users>(`${environment.backendUrl}/user/login`, {
      'username': username,
      'password': password
    }, {
      withCredentials: true
    });
  }

}
