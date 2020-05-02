import { Component, OnInit, ViewChild } from '@angular/core';

import { Debate } from '../dto/debate.dto';
import { DebateService } from '../services/debate.service';
import { LoadMoreComponent } from '../common/load-more/load-more.component';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss'],
})
export class HomepageComponent implements OnInit {
  popularDebates: Debate[] = [];
  popularPage = 0;
  newDebates: Debate[] = [];
  newPage = 0;
  hotDebates: Debate[] = [];
  hotPage = 0;
  
  @ViewChild('loadPopularDebate') popularDebatesLoader: LoadMoreComponent;
  @ViewChild('loadNewDebate') newDebatesLoader: LoadMoreComponent;
  @ViewChild('loadHotDebate') hotDebatesLoader: LoadMoreComponent;

  
  constructor(private debateService: DebateService) {}
  
  ngOnInit(): void {
    this.debateService.getPopularDebates(10, 0).subscribe(data => this.popularDebates = data);
    this.debateService.getHotDebates(10, 0).subscribe(data => this.hotDebates = data);
    this.debateService.getNewDebates(10, 0).subscribe(data => this.newDebates = data);
  }

  loadPopularDebates() {
    this.popularPage += 1;
    this.debateService.getPopularDebates(10, this.popularPage).subscribe(data => {
      this.popularDebates = this.popularDebates.concat(data);
      this.popularDebatesLoader.isLoading = false;
      if (data.length < 10){
        this.popularDebatesLoader.hasMore = false;
      }
    });
  }

  loadNewDebates() {
    this.newPage += 1;
    this.debateService.getNewDebates(10, this.newPage).subscribe(data => {
      this.newDebates = this.newDebates.concat(data);
      this.popularDebatesLoader.isLoading = false;
      if (data.length < 10){
        this.newDebatesLoader.hasMore = false;
      }
    });
  }

  loadHotDebates() {
    this.hotPage += 1;
    this.debateService.getHotDebates(10, this.hotPage).subscribe(data => {
      this.hotDebates = this.hotDebates.concat(data);
      this.hotDebatesLoader.isLoading = false;
      if (data.length < 10){
        this.hotDebatesLoader.hasMore = false;
      }
    });
  }

}
