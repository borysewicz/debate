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
  newDebates: Debate[] = [];
  hotDebates: Debate[] = [];

  constructor(private debateService: DebateService) {}

  ngOnInit(): void {
    this.debateService.getPopularDebates(10, 1).subscribe(data => this.popularDebates = data);
    this.debateService.getHotDebates(10, 1).subscribe(data => this.hotDebates = data);
    this.debateService.getNewDebates(10, 1).subscribe(data => this.newDebates = data);
  }
}
