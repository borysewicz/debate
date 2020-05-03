import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-load-more',
  templateUrl: './load-more.component.html',
  styleUrls: ['./load-more.component.scss'],
})
export class LoadMoreComponent implements OnInit {
  @Output() private loadRequested = new EventEmitter<void>();
  @Input() isLoading: boolean;

  constructor() {}

  ngOnInit(): void {}

  public emitLoadRequest() {
    this.loadRequested.emit();
    this.isLoading = true;
  }
}
