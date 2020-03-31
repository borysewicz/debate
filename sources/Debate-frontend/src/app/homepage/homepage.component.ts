import { Component, OnInit } from '@angular/core';

import { Debate } from '../dto/debate.dto';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  debate: Debate;
  
  constructor() { 
    this.debate = {
      id: 1,  
      title: 'Wybory prezydenckie powinny być przełożone',
      description: 'Do maja zostały 2 miesiące, wirus COVID-19 blokuje życie publiczne, a partia rządząca dalej chce przeprowadzić wybory. Czy słusznie? Zwolennicy'
       + ' partii rządzącej są za przestrzeganiem Konstytucji, opozycja zwraca uwagę na wyjątkowy stan, w jakim obecnie się znajdujemy.',
      imageSrc:'https://media.wplm.pl/thumbs/ODI1L3VfMS9jY19iNjg3MS9wLzIwMTkvMDkvMTgvODI4LzUwOC80NjU1YTY0MWRhYjI0NzZlYWIyYjIyMGNhNDhmODM1YS5wbmc=.png',
      argumentCount: 5,
      commentCount: 5,
      voteCount: 2,
      participantCount: 15,
      viewCount: 156,
      mainTags: ['Polityka', 'COVID-19', 'Wybory'],
      allTags: ['Polityka', 'COVID-19', 'Wybory', 'Zdrowie', 'Gospodarka']
    }
  }

  ngOnInit(): void {
  }

}
