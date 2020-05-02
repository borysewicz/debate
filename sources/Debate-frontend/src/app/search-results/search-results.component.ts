import { Component, OnInit, Output, OnDestroy } from '@angular/core';
import { Debate } from '../dto/debate.dto';
import { DebateService } from '../services/debate.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

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
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.queryParamsSubscription = this.activatedRoute.queryParams.subscribe(
      (queryParams) => {
        console.log(queryParams);
        if (queryParams.searchTag && queryParams.searchTag.length) {
          this.debateService
            .getDebatesByTags(queryParams.searchTag)
            .subscribe((debates) => {
              this.filteredDebates = debates;
            });
        } else {
          let name = queryParams.searchName;
          this.debateService.getDebatesByName(name).subscribe((debates) => {
            this.filteredDebates = debates;
          });
        }
      }
    );
  }

  ngOnDestroy(): void {
    this.queryParamsSubscription.unsubscribe();
  }
}
