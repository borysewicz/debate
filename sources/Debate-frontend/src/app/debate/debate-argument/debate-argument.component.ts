import { Component, Input, OnInit } from '@angular/core';

import { Comment } from '../../dto/comment.dto';
import { Argument, ArgumentAttitude } from './../../dto/argument.dto';
import { UserVote } from './../../dto/userVote.enum';
import { ArgumentService } from './../../services/argument.service';
import { CommentService } from './../../services/comment.service';

@Component({
  selector: 'app-debate-argument',
  templateUrl: './debate-argument.component.html',
  styleUrls: ['./debate-argument.component.scss'],
})
export class DebateArgumentComponent implements OnInit {
  readonly ArgumentAttitude = ArgumentAttitude;
  readonly UserVote = UserVote;

  @Input() argument: Argument;
  iconName: string;
  isPanelExpanded: boolean;
  comments: Comment[];

  isCommentSectionLoading: boolean;
  isCommentLoadingError: boolean;
  isRefreshing: boolean;

  constructor(private argumentService: ArgumentService, private commentService: CommentService) {}

  ngOnInit(): void {
    this.isCommentSectionLoading = false;
    this.isCommentLoadingError = false;
    this.comments = [];
    this.iconName =
      this.argument.attitude === ArgumentAttitude.POSITIVE ? 'add' : 'remove';
  }

  onRateButtonPressed(event: Event, rateValue: UserVote) {
    if (this.argument.userVote !== rateValue) {
      this.argument.userVote = rateValue;
    } else {
      this.argument.userVote = UserVote.NONE;
      rateValue = UserVote.NONE;
    }

    this.argumentService.rateArgument(this.argument._id, rateValue).subscribe(rates => {
      this.argument.upVotes = rates.upvotes;
      this.argument.downVotes = rates.downvotes;
    });
    event.stopPropagation();
  }

  getComments(): void {
    this.isCommentSectionLoading = true;
    this.isCommentLoadingError = false;
    this.commentService.getCommentsForArgument(this.argument._id).subscribe(comments => {
      this.comments = comments;
      this.isRefreshing = false;
      this.isCommentSectionLoading = false;
    },
    err => {
      this.isCommentLoadingError = true;
      this.isRefreshing = false;
      this.isCommentSectionLoading = false;
    });
  }

  onReloadCommentsButtonClicked() {
    this.isRefreshing = true;
    this.getComments();
  }
}
