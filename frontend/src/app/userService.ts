import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class UserService {
  constructor (
    private http: HttpClient
  ) {}

  // getUser() {
  //   return this.http.get(`https://conduit.productionready.io/api/profiles/eric`)
  //     .map((res:Response) => res.json());
  // }

  saveUser(userData) {
    this.http.post('http://localhost:8080/users/', userData)
      .subscribe();
  }

}
