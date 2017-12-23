import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {environment} from "../../environments/environment";
import {Sector} from "../model/sector";

@Injectable()
export class SectorService {

  apiUrl = environment.apiUrl;

  constructor (
    private http: HttpClient
  ) {}

  getSectors(): Observable<Sector[]> {
    return this.http.get(`${this.apiUrl}/sectors/`)
      .catch((error: any) => Observable.throw(error || 'Server error'));
  }

}
