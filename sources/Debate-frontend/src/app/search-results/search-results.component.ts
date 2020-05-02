import { Component, OnInit, Output, OnDestroy } from '@angular/core';
import { Debate } from '../dto/debate.dto';
import { Observable, Subscription } from 'rxjs';
import { DebateService } from '../services/debate.service';
import { EventEmitter } from 'protractor';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss'],
})
export class SearchResultsComponent implements OnInit, OnDestroy {
  filteredDebates: Debate[];
  private queryParamsSubscription: Subscription;

  constructor(
    private debateService: DebateService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.queryParamsSubscription = this.activatedRoute.queryParams.subscribe(
      (queryParams) => {
        let name = queryParams.searchName;
        this.debateService
          .getDebatesByName(name)
          .subscribe((debates) => {(this.filteredDebates = debates);
          console.log(debates)});
      }
    );
  }

  ngOnDestroy(): void {
    this.queryParamsSubscription.unsubscribe();
  }
}
