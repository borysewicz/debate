import { Observable, Subscription } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TimeagoIntl } from 'ngx-timeago';
import { strings as polishStrings } from 'ngx-timeago/language-strings/pl';
import { map, startWith } from 'rxjs/operators';
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  debateControl = new FormControl('');
  debates: string[] = [
    'Wykłady to marnowanie czasu',
    'Dostęp do broni powinien być powszechny',
    'Aborcja powinna być legalna',
  ];
  filteredDebates: Observable<string[]>;
  loginLink: string;
  isSignedOnSubscription: Subscription;
  isLogged: boolean;

  constructor(private authService: AuthService, private router: Router, intl: TimeagoIntl) {
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
    this.isSignedOnSubscription = this.authService.isSignedIn.subscribe(
      (tmp) => {
        this.loginLink = tmp ? '/home' : '/login';
        this.isLogged = tmp;
      }
    );
  }

  private _filter(newTag: string) {
    const filterValue = newTag.toLowerCase();
    return this.debates.filter((debate) =>
      debate.toLowerCase().includes(filterValue)
    );
  }

  ngOnDestroy() {
    this.isSignedOnSubscription.unsubscribe();
  }

  logOut() {
    this.authService.logOut();
    this.router.navigate(['/home']);
  }

  searchDebateByName() {
    if (this.debateControl.value !== '') {
      this.router.navigate(['/searchResults'], {
        queryParams: { searchName: this.debateControl.value },
      });
    }
  }
}
