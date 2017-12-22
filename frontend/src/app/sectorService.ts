import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Sector} from './sector';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class SectorService {
  constructor (
    private http: HttpClient
  ) {}

  getSectors(): Observable<Sector[]> {
    return this.http.get('http://localhost:8080/sectors/')
      .catch((error: any) => Observable.throw(error || 'Server error'));
  }

}
