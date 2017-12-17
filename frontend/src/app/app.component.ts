import {Component} from '@angular/core';
import {User} from './user';
import {NgForm} from '@angular/forms';
import {UserService} from './userService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [UserService]
})
export class AppComponent {

  constructor(private userService: UserService) { }

  user = new User(1, '2', [1, 2], true);
  sectors = [
    {
      'id': 1,
      'name': 'Manufacturing',
      'parentSectorId': null,
      'children': [
        {
          'id': 2,
          'name': 'Construction materials',
          'parentSectorId': 1,
          'children': []
        },
        {
          'id': 3,
          'name': 'Electronics and Optics',
          'parentSectorId': 1,
          'children': []
        },
        {
          'id': 4,
          'name': 'Food and Beverage',
          'parentSectorId': 1,
          'children': [
            {
              'id': 5,
              'name': 'Bakery & confectionery products',
              'parentSectorId': 4,
              'children': []
            },
            {
              'id': 6,
              'name': 'Beverages',
              'parentSectorId': 4,
              'children': []
            },
          ]
        }
      ]
    }
  ];

  onSubmit (data: NgForm) {
    console.log('Register user');
    console.log(data.value);
    this.userService.saveUser(data.value);
  }
  public send() {
    console.log('Clicked');
  }
}
