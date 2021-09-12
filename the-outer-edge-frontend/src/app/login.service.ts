import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Users } from '../model/user';
import { environment } from 'src/environments/environment';

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
    return this.hc.post<Users>(`${environment.backendUrl}/TheOuterEdge/login`, {
      'username': username,
      'password': password
    }, {
      withCredentials: true
    });
  }

  checkIfLoggedIn(): Observable<Users> {
    return this.hc.get<Users>(`${environment.backendUrl}/TheOuterEdge/currentuser`, {
      withCredentials: true
    });
  }

 

  logout() {
    return this.hc.post(`${environment.backendUrl}/TheOuterEdge/logout`, {}, {
      withCredentials: true
    });
  }

}
