import {Injectable} from '@angular/core';
import 'rxjs/add/operator/map';
import {HttpClient} from '@angular/common/http';
import {CookieService} from 'angular2-cookie/core';
import {User} from './user';
import {Observable} from 'rxjs/Observable';
import {RequestOptions} from '@angular/http';

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {
  }

  saveUser(userData) {

    const userId = localStorage.getItem('userId');

    if (userId) {
      userData.id = userId;
      this.http.put('http://localhost:8080/users/', userData)
        .subscribe();
    } else {
      this.http.post('http://localhost:8080/users/', userData)
        .map(response => response as User)
        .subscribe(
          (data) => localStorage.setItem('userId', data.id.toString()),
          (error) => console.log(error)
        );
    }
  }

  findUser(id: string): Observable<User> {
    return this.http.get(`http://localhost:8080/users/${id}`)
      .catch((error: any) => Observable.throw(error || 'Server error'));
  }

}
