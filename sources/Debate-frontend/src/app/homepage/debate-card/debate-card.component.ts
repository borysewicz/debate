import { Component, OnInit, Input } from '@angular/core';

import { Debate } from '../../dto/debate.dto';
import { DebateService } from 'src/app/services/debate.service';
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-debate-card',
  templateUrl: './debate-card.component.html',
  styleUrls: ['./debate-card.component.scss']
})
export class DebateCardComponent implements OnInit {
  @Input() debate: Debate;
  sliceEnd = 100;
  isExpanded = false;
  expandString = 'Rozwiń';
  imageEndpoint = environment.api + '/debate/cover/';

  constructor(private debateService: DebateService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
  }

  toggleExpand() {
    if (this.isExpanded) {
      this.sliceEnd = 100;
      this.expandString = 'Rozwiń';
      this.isExpanded = false;
    } else {
        this.sliceEnd = this.debate.content.length;
        this.expandString = 'Zwiń';
        this.isExpanded = true;
    }
  }

 getCoverUrl() {
   const urlPath = this.imageEndpoint + this.debate._id;
   return this.sanitizer.bypassSecurityTrustStyle('url(' + urlPath + '), url(assets/no_cover.png)');
 }

}
