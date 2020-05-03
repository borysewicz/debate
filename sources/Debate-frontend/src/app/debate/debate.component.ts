import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

import { Argument, ArgumentAttitude } from '../dto/argument.dto';
import { Comment } from '../dto/comment.dto';
import { DebateService } from '../services/debate.service';
import { Debate } from './../dto/debate.dto';
import { ArgumentService } from './../services/argument.service';
import { CommentService } from './../services/comment.service';

@Component({
  selector: 'app-debate',
  templateUrl: './debate.component.html',
  styleUrls: ['./debate.component.scss'],
})
export class DebateComponent implements OnInit, OnDestroy {
  private routeSubscription: Subscription;
  debate: Debate;
  pros: Argument[];
  cons: Argument[];
  isCommentSectionOn: boolean;
  comments: Comment[];

  isArgumentSectionLoading: boolean;
  isArgumentSectionRefreshing: boolean;
  isArgumentSectionLoadingError: boolean;

  isCommentSectionLoading: boolean;
  isCommentSectionLoadingError: boolean;
  isCommentSectionRefreshing: boolean;

  constructor(
    private debateService: DebateService,
    private argumentService: ArgumentService,
    private commentService: CommentService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.isCommentSectionLoadingError = false;
    this.isCommentSectionLoading = false;
    this.isCommentSectionRefreshing = false;
    this.isCommentSectionOn = false;
    this.comments = [];
    this.pros = [];
    this.cons = [];
    this.routeSubscription = this.route.params.subscribe((params) => {
      this.updateModel(params.id);
    });
  }

  private updateModel(id: string) {
    this.debateService.getDebateById(id).subscribe((debate) => {
      this.debate = debate;
      if (this.isCommentSectionOn) {
        this.getComments();
      } else {
        this.getArguments();
      }
    });
  }

  private getArguments(): void {
    this.isArgumentSectionLoading = true;
    this.isArgumentSectionLoadingError = false;
    this.argumentService.getArgumentsForDebate(this.debate._id).subscribe(
      (debateArgs) => {
        this.pros = debateArgs
          .filter((x) => x.attitude === ArgumentAttitude.POSITIVE)
          .sort((a, b) => b.upVotes - a.upVotes);
        this.cons = debateArgs
          .filter((x) => x.attitude === ArgumentAttitude.NEGATIVE)
          .sort((a, b) => b.upVotes - a.upVotes);
        this.isArgumentSectionRefreshing = false;
        this.isArgumentSectionLoading = false;
      },
      (err) => {
        this.isArgumentSectionLoadingError = true;
        this.isArgumentSectionRefreshing = false;
        this.isArgumentSectionLoading = false;
      }
    );
  }

  private getComments(): void {
    this.isCommentSectionLoading = true;
    this.isCommentSectionLoadingError = false;
    this.commentService.getCommentsForDebate(this.debate._id).subscribe(
      (comments) => {
        this.comments = comments;
        this.isCommentSectionRefreshing = false;
        this.isCommentSectionLoading = false;
      },
      (err) => {
        this.isCommentSectionLoadingError = true;
        this.isCommentSectionRefreshing = false;
        this.isCommentSectionLoading = false;
      }
    );
  }

  onReloadCommentsButtonClicked() {
    this.isCommentSectionRefreshing = true;
    this.getComments();
  }

  onReloadArgumentsButtonClicked() {
    this.isArgumentSectionRefreshing = true;
    this.getArguments();
  }

  onSectionButtonClicked() {
    this.isCommentSectionOn = !this.isCommentSectionOn;

    if (this.isCommentSectionOn) {
      this.getComments();
    } else {
      this.getArguments();
    }
  }

  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
  }
}
