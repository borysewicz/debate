import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { delay } from 'rxjs/operators';

import { Argument, ArgumentAttitude } from '../dto/argument.dto';
import { Comment } from '../dto/comment.dto';
import { UserVote } from './../dto/userVote.enum';

@Injectable({
  providedIn: 'root',
})
export class ArgumentService {
  private endpoint = 'http://localhost:8080/api/argument';
  private readonly dummy = new BehaviorSubject<Argument[]>([
    {
      _id: '31231311',
      authorName: 'Marcel',
      userVote: UserVote.NONE,
      title: 'Pro 1',
      content:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.POSITIVE,
      upVotes: 3123,
      downVotes: 312,
      lastEditTime: new Date()
    },
    {
      _id: '54523424',
      authorName: 'Jan',
      userVote: UserVote.POSITIVE,
      title: 'Con 1',
      content:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.NEGATIVE,
      upVotes: 513,
      downVotes: 12,
      lastEditTime: new Date()
    },
    {
      _id: '987979789',
      authorName: 'Julian',
      userVote: UserVote.NEGATIVE,
      title: 'Pro 2',
      content:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.POSITIVE,
      upVotes: 31,
      downVotes: 2,
      lastEditTime: new Date()
    },
    {
      _id: '253452435',
      authorName: 'Marcel',
      userVote: UserVote.NONE,
      title: 'Con 3',
      content:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.NEGATIVE,
      upVotes: 321,
      downVotes: 43,
      lastEditTime: new Date()
    },
    {
      _id: '6434124132',
      authorName: 'Michał',
      userVote: UserVote.POSITIVE,
      title: 'Con 2',
      content:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.NEGATIVE,
      upVotes: 31131,
      downVotes: 3123,
      lastEditTime: new Date()
    },
  ]);
  private readonly commentDummy = new BehaviorSubject<Comment[]>([
    {
      _id: '312311',
      content: 'W punkt',
      publishedAt: new Date(),
      authorName: 'Jan',
      activityId: '54523424',
      userVote: UserVote.POSITIVE,
    },
    {
      _id: '3123154',
      content: 'Nie zgadzam się, ponieważ to kompletnie błędny punkt widzenia',
      publishedAt: new Date(),
      authorName: 'Jan',
      activityId: '54523424',
      userVote: UserVote.NEGATIVE,
    },
    {
      _id: '5423113',
      content: 'Nie zgadzam się, ponieważ to zdecydowanie bzdurny pomysł',
      publishedAt: new Date(),
      authorName: 'Jan',
      activityId: '54523424',
      userVote: UserVote.NONE,
    },
    {
      _id: '456786',
      content: 'Zawsze można to zrobić w bezpieczny sposób.',
      publishedAt: new Date(),
      authorName: 'Jan',
      activityId: '54523424',
      userVote: UserVote.POSITIVE,
    },
    {
      _id: '876543',
      content: 'Nie zgadzam się, ponieważ to kompletnie błędny punkt widzenia. Bezpieczeństwo jest rzeczą wtórną i może być zapewnione bez problemu w każdym momencie przez wspaniały rząd.',
      publishedAt: new Date(),
      authorName: 'Jan',
      activityId: '54523424',
      userVote: UserVote.POSITIVE,
    },
    {
      _id: '123123',
      content: 'Nie zgadzam się, ponieważ to kompletnie błędny punkt widzenia',
      publishedAt: new Date(),
      authorName: 'Marek',
      activityId: '54523424',
      userVote: UserVote.POSITIVE,
    },
    {
      _id: '123123',
      content: 'Nie zgadzam się, ponieważ to kompletnie błędny punkt widzenia',
      publishedAt: new Date(),
      authorName: 'Marek',
      activityId: '54523424',
      userVote: UserVote.POSITIVE,
    }
  ]);

  constructor(private http: HttpClient) {}

  getArgumentsForDebate(id: string): Observable<Argument[]> {
    return this.dummy.asObservable();
  }

  getCommentsForArgument(id: string): Observable<Comment[]> {
     return this.commentDummy.asObservable().pipe(delay(1000));
  }

  rateArgument(argumentId: string, userVote: UserVote): void {}

  sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
      currentDate = Date.now();
    } while (currentDate - date < milliseconds);
  }
}
