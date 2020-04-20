import { Component, OnInit, Input } from '@angular/core';

import { ArgumentService } from './../../services/argument.service';
import { UserVote } from './../../dto/userVote.enum';
import { Argument, ArgumentAttitude } from './../../dto/argument.dto';
import { Comment } from '../../dto/comment.dto';

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

  constructor(private argumentService: ArgumentService) {}

  ngOnInit(): void {
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

    this.argumentService.rateArgument(this.argument._id, rateValue);
    event.stopPropagation();
  }

  getComments(): void {
    this.argumentService.getCommentsForArgument(this.argument._id).subscribe(comments => this.comments = comments);
  }
}
