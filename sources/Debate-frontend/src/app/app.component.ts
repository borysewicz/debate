import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TimeagoIntl } from 'ngx-timeago';
import { strings as polishStrings } from 'ngx-timeago/language-strings/pl';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  debateControl = new FormControl('');
  debates: string[] = [
    'Wykłady to marnowanie czasu',
    'Dostęp do broni powinien być powszechny',
    'Aborcja powinna być legalna'
  ];
  filteredDebates: Observable<string[]>;

  constructor(intl: TimeagoIntl) {
    intl.strings = polishStrings;
    intl.changes.next();
  }

  ngOnInit(): void {
    this.filteredDebates = this.debateControl.valueChanges.pipe(
      // tslint:disable-next-line: deprecation
      startWith(null),
      map((debate: string | null) =>
        debate ? this._filter(debate) : this.debates.slice()
      )
    );
  }

  private _filter(newTag: string) {
    const filterValue = newTag.toLowerCase();
    return this.debates.filter(debate =>
      debate.toLowerCase().includes(filterValue)
    );
  }

}
