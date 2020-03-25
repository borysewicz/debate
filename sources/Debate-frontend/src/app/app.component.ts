import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  debateSearch = new FormGroup({
    debate: new FormControl('')
  });
  debateSearchDummy: string[] = [
    'Wykłady to marnowanie czasu',
    'Dostęp do broni powinien być powszechny',
    'Aborcja powinna być legalna'
  ];
}
