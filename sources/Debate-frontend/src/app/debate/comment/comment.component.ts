import { Component, OnInit, Input } from '@angular/core';

import { Comment } from '../../dto/comment.dto';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss'],
})
export class CommentComponent implements OnInit {
  readonly colors: string[] = [
    '',
    'avatar-green',
    'avatar-purple',
    'avatar-burlywood',
    'avatar-pink',
  ];
  @Input() comment: Comment;
  initials: string;

  constructor() {}

  ngOnInit(): void {
    this.initials = this.comment.authorName
      .replace(/[a-z]/g, '')
      .substring(0, 2) || 'A';
  }

  getCustomAvatarColor() {
    return this.colors[this.initials.charCodeAt(0) % this.colors.length];
  }
}
