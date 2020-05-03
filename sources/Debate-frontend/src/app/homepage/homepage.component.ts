import { Component, OnInit } from '@angular/core';

import { Debate } from '../dto/debate.dto';
import { DebateService } from '../services/debate.service';

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

  hasMorePopular: boolean;
  hasMoreHot: boolean;
  hasMoreNew: boolean;
  isPopularLoading: boolean;
  isHotLoading: boolean;
  isNewLoading: boolean;

  constructor(private debateService: DebateService) {}

  ngOnInit(): void {
    this.isHotLoading = false;
    this.isNewLoading = false;
    this.isPopularLoading = false;
    this.debateService.getPopularDebates(10, 0).subscribe(data => {
      this.popularDebates = data;
      this.hasMorePopular = data.length >= 10;
    });
    this.debateService.getHotDebates(10, 0).subscribe(data => {
      this.hotDebates = data;
      this.hasMoreHot = data.length >= 10;
    });
    this.debateService.getNewDebates(10, 0).subscribe(data => {
      this.newDebates = data;
      this.hasMoreNew = data.length >= 10;
    });
  }

  loadPopularDebates() {
    this.popularPage += 1;
    this.debateService.getPopularDebates(10, this.popularPage).subscribe(data => {
      this.popularDebates = this.popularDebates.concat(data);
      this.isPopularLoading = false;
      if (data.length < 10) {
        this.hasMorePopular = false;
      }
    });
  }

  loadNewDebates() {
    this.newPage += 1;
    this.debateService.getNewDebates(10, this.newPage).subscribe(data => {
      this.newDebates = this.newDebates.concat(data);
      this.isNewLoading = false;
      if (data.length < 10) {
        this.hasMoreNew = false;
      }
    });
  }

  loadHotDebates() {
    this.hotPage += 1;
    this.debateService.getHotDebates(10, this.hotPage).subscribe(data => {
      this.hotDebates = this.hotDebates.concat(data);
      this.isHotLoading = false;
      if (data.length < 10) {
        this.hasMoreHot = false;
      }
    });
  }

}
