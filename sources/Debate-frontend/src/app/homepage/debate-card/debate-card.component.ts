import { Component, OnInit, Input } from '@angular/core';

import { Debate } from '../../dto/debate.dto';
import { DebateService } from 'src/app/services/debate.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-debate-card',
  templateUrl: './debate-card.component.html',
  styleUrls: ['./debate-card.component.scss']
})
export class DebateCardComponent implements OnInit {
  @Input() debate: Debate;
  sliceEnd: number = 100;
  isExpanded: boolean = false;
  expandString: string = "Rozwiń";

  constructor(private debateService: DebateService, private sanitizer: DomSanitizer) { }
  
  ngOnInit(): void {
  }

  toggleExpand(){
    console.log("Exoand");
    if (this.isExpanded){
      this.sliceEnd = 100;
      this.expandString = "Rozwiń";
      this.isExpanded = false;
    } else {
        this.sliceEnd = this.debate.content.length;
        this.expandString = "Zwiń"
        this.isExpanded = true;
    }
  }

 getCoverUrl(){
   const urlPath = 'http://localhost:8080/api/debate/cover/' + this.debate._id;
   return this.sanitizer.bypassSecurityTrustStyle('url(' + urlPath + ')');
 }

}
