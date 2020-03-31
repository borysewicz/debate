import { Component, OnInit, Input } from '@angular/core';

import {Debate} from '../../dto/debate.dto';

@Component({
  selector: 'app-debate-card',
  templateUrl: './debate-card.component.html',
  styleUrls: ['./debate-card.component.scss']
})
export class DebateCardComponent implements OnInit {

  constructor() { }

  @Input() debate: Debate;
  sliceEnd: number = 100;
  isExpanded: boolean = false;
  expandString: string = "Rozwiń";

  ngOnInit(): void {
  }


  toggleExpand(){
    console.log("Exoand");
    if (this.isExpanded){
      this.sliceEnd = 100;
      this.expandString = "Rozwiń";
      this.isExpanded = false;
    }else {
        this.sliceEnd = this.debate.description.length;
        this.expandString = "Zwiń"
        this.isExpanded = true;
    }
    console.log(this.debate);
  }

}
