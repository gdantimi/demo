import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {AlertService} from "./service/alertService";
import {UserService} from "./service/userService";
import {SectorService} from "./service/sectorService";
import 'rxjs/add/operator/map';
import {User} from "./model/user";
import {Sector} from "./model/sector";
import {environment} from "../environments/environment.prod";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [UserService, SectorService, AlertService]
})
export class AppComponent implements OnInit {
  constructor(private userService: UserService,
              private sectorService: SectorService,
              private alertService: AlertService) {
  }

  user: User = new User();
  sectors: Sector[] = [];
  serverPortNumber = environment.apiUrl;

  ngOnInit(): void {
    this.loadSectors();
    this.loadUser();
    this.alertService
  }

  onSubmit(userForm: NgForm) {
    if (userForm.valid) {
      const userId = localStorage.getItem('userId');
      const userData = userForm.value;

      if (userId) {
        userData.id = userId;
        this.updateUser(userData, userForm);
      } else {
        this.saveUser(userData, userForm);
      }

    } else {
      console.log('There are errors in the form');
    }
  }

  private updateUser(userData: any, userForm: NgForm) {
    this.userService.updateUser(userData)
      .map(response => response as User)
      .subscribe(
        () => {
          this.alertService.success("User updated!")
        },
        (error) => {
          this.handleError(error, userForm);
        }
      );
  }

  private saveUser(userData: any, userForm: NgForm) {
    this.userService.saveUser(userData)
      .map(response => response as User)
      .subscribe(
        (data) => {
          localStorage.setItem('userId', data.id.toString());
          this.alertService.success("User saved!")
        },
        (error) => {
          this.handleError(error, userForm)
        }
      );
  }

  private handleError(error, userForm: NgForm) {
    let remoteError = error.error.errors[0];
    userForm.controls[remoteError.field].setErrors({'uniqueConstrainViolation': true});
    console.log(remoteError.defaultMessage);
  }

  private loadSectors() {
    this.sectorService.getSectors()
      .subscribe(
        sectors => this.sectors = sectors,
        err => console.log(err)
      );
  }

  private loadUser() {
    const userId = localStorage.getItem('userId');

    if (userId) {
      this.userService.findUser(userId).subscribe(
        user => this.user = user,
        err => console.log(err),
      );
    }
  }

  clearSession() {
    localStorage.clear();
  }

}
