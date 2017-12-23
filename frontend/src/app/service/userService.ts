import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {environment} from "../../environments/environment";
import {User} from "../model/user";


@Injectable()
export class UserService {

  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  saveUser(userData) {

    const userId = localStorage.getItem('userId');

    if (userId) {
      userData.id = userId;
      return this.http.put(`${this.apiUrl}/users/`, userData);
    } else {
      return this.http.post(`${this.apiUrl}/users/`, userData);
    }
  }

  updateUser(userData){
    return this.http.put(`${this.apiUrl}/users/`, userData);
  }

  findUser(id: string): Observable<User> {
    return this.http.get(`${this.apiUrl}/users/${id}`)
      .catch((error: any) => Observable.throw(error || 'Server error'));
  }

}
