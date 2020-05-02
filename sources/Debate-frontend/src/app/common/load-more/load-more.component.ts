import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-load-more',
  templateUrl: './load-more.component.html',
  styleUrls: ['./load-more.component.scss']
})
export class LoadMoreComponent implements OnInit {

  hasMore = true;
  @Output() private loadRequested: EventEmitter<any> = new EventEmitter();
  isLoading = false;

  constructor() { }

  ngOnInit(): void {
  }

  public emitLoadRequest() {
    this.loadRequested.emit();
    this.isLoading = true;
  }

}
