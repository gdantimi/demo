import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
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

  public send() {
    console.log('Clicked');
  }
}
