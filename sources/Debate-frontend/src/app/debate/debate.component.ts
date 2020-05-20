import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

import { Argument, ArgumentAttitude } from '../dto/argument.dto';
import { Comment } from '../dto/comment.dto';
import { DebateService } from '../services/debate.service';
import { Debate } from './../dto/debate.dto';
import { ArgumentService } from './../services/argument.service';
import { CommentService } from './../services/comment.service';
import { AddArgumentDialogComponent } from './debate-argument/add-argument-dialog/add-argument-dialog.component';

@Component({
  selector: 'app-debate',
  templateUrl: './debate.component.html',
  styleUrls: ['./debate.component.scss'],
})
export class DebateComponent implements OnInit, OnDestroy {
  constructor(
    private debateService: DebateService,
    private argumentService: ArgumentService,
    private commentService: CommentService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {}

  ArgumentAttitude = ArgumentAttitude;
  private routeSubscription: Subscription;
  private signedInSubscription: Subscription;
  debate: Debate;
  pros: Argument[];
  cons: Argument[];
  isCommentSectionOn: boolean;
  isSignedIn: boolean;
  comments: Comment[];

  hasMoreArguments: boolean;
  private readonly pageLimit: number = 2;
  private argumentPage: number;

  isArgumentSectionLoading: boolean;
  isArgumentSectionRefreshing: boolean;
  isArgumentSectionLoadingError: boolean;

  isCommentSectionLoading: boolean;
  isCommentSectionLoadingError: boolean;
  isCommentSectionRefreshing: boolean;

  ngOnInit(): void {
    this.isCommentSectionLoadingError = false;
    this.isCommentSectionLoading = false;
    this.isCommentSectionRefreshing = false;
    this.isCommentSectionOn = false;
    this.hasMoreArguments = false;
    this.argumentPage = 0;
    this.comments = [];
    this.pros = [];
    this.cons = [];
    this.signedInSubscription = this.authService.isSignedIn.subscribe(
      (isSignedIn) => (this.isSignedIn = isSignedIn) // isSignedIn)
    );
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
    this.argumentService
      .getArgumentsForDebate(this.debate._id, this.pageLimit, this.argumentPage)
      .subscribe(
        (debateArgs) => {
          const tempPros = debateArgs.filter(
            (x) => x.attitude === ArgumentAttitude.POSITIVE
          );
          this.pros = this.pros.concat(
            tempPros
              .sort((a, b) => b.upVotes - a.upVotes)
          );
          const tempCons = debateArgs.filter(
            (x) =>
              x.attitude === ArgumentAttitude.NEGATIVE
          );
          this.cons = this.cons.concat(
            tempCons.sort((a, b) => b.upVotes - a.upVotes)
          );
          this.isArgumentSectionRefreshing = false;
          this.isArgumentSectionLoading = false;
          this.hasMoreArguments =
            tempPros.length === this.pageLimit ||
            tempCons.length === this.pageLimit;
        },
        (err) => {
          this.isArgumentSectionLoadingError = true;
          this.isArgumentSectionRefreshing = false;
          this.isArgumentSectionLoading = false;
          this.hasMoreArguments = false;
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
      this.argumentPage = 0;
      this.pros = [];
      this.cons = [];
      this.getComments();
    } else {
      this.getArguments();
    }
  }

  onCommentAdded(comment: string) {
    this.commentService
      .addDebateComment(this.debate._id, comment)
      .subscribe((newComment) => {
        this.comments.push(newComment);
      });
  }

  onLoadMoreArguments() {
    this.argumentPage++;
    this.getArguments();
  }

  onAddArgumentButton(argumentType: ArgumentAttitude) {
    const dialogRef = this.dialog.open(AddArgumentDialogComponent, {
      minWidth: '300px',
      minHeight: '300px',
      data: {
        type: argumentType === ArgumentAttitude.POSITIVE ? 'za' : 'przeciw',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.argumentService
        .addArgument({
          debateId: this.debate._id,
          attitude: argumentType,
          title: result.title,
          content: result.content,
        })
        .subscribe((newArgument) => {
          if (newArgument.attitude === ArgumentAttitude.POSITIVE) {
            this.pros.push(newArgument);
          } else {
            this.cons.push(newArgument);
          }
        });
    });
  }

  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
    this.signedInSubscription.unsubscribe();
  }
}
