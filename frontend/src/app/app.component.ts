import {Component, OnInit} from '@angular/core';
import {User} from './user';
import {NgForm} from '@angular/forms';
import {UserService} from './userService';
import {SectorService} from './sectorService';
import {CookieService} from 'angular2-cookie/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [UserService, SectorService, CookieService]
})
export class AppComponent implements OnInit {

  constructor(private userService: UserService,
              private sectorService: SectorService) { }

  user = new User();
  sectors = [];

  onSubmit (userForm: NgForm) {
    if (userForm.valid) {
      console.log('Register user');
      console.log(userForm.value);
      this.userService.saveUser(userForm.value);
    } else {
      console.log('There are errors in the form');
    }
  }

  ngOnInit(): void {
    this.sectorService.getSectors()
      .subscribe(
        sectors => this.sectors = sectors,
        err => console.log(err)
        );

    const userId = localStorage.getItem('userId');

    if (userId) {
      this.userService.findUser(userId).subscribe(
        user => this.user = user,
        err => console.log(err)
      );
    }
  }

}
