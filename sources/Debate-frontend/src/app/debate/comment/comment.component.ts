import { Component, Input, OnInit } from '@angular/core';

import { Comment } from '../../dto/comment.dto';
import { UserVote } from './../../dto/userVote.enum';
import { CommentService } from './../../services/comment.service';

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
  UserVote = UserVote;

  constructor(private commentService: CommentService) {}

  ngOnInit(): void {
    this.initials = this.comment.author
      .replace(/[a-z]/g, '')
      .substring(0, 2) || this.comment.author.substring(0, 1) || 'A';
  }

  getCustomAvatarColor() {
    return this.colors[this.initials.charCodeAt(0) % this.colors.length];
  }

  rateComment(rate: UserVote): void {
    if (this.comment.userVote !== rate) {
      this.comment.userVote = rate;
    } else {
      this.comment.userVote = UserVote.NONE;
      rate = UserVote.NONE;
    }

    this.commentService.rateComment(this.comment._id, rate).subscribe(votes => {
      this.comment.upvotes = votes.upvotes;
      this.comment.downvotes = votes.downvotes;
    });
  }
}
