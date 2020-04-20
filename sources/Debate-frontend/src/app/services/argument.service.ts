import { UserVote } from './../dto/userVote.enum';
import { Observable, BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';
import { Argument, ArgumentAttitude } from '../dto/argument.dto';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ArgumentService {

  private endpoint = 'http://localhost:8080/api/argument';
  private readonly dummy = new BehaviorSubject<Argument[]>([
    {
      _id: '31231311',
      authorName: 'Marcel',
      userVote: UserVote.NONE,
      title: 'Pro 1',
      content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.POSITIVE,
      upVotes: 3123,
      downVotes: 312
    },
    {
      _id: '54523424',
      authorName: 'Jan',
      userVote: UserVote.POSITIVE,
      title: 'Con 1',
      content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.NEGATIVE,
      upVotes: 513,
      downVotes: 12
    },
    {
      _id: '987979789',
      authorName: 'Julian',
      userVote: UserVote.NEGATIVE,
      title: 'Pro 2',
      content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.POSITIVE,
      upVotes: 31,
      downVotes: 2
    },
    {
      _id: '253452435',
      authorName: 'Marcel',
      userVote: UserVote.NONE,
      title: 'Con 3',
      content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.NEGATIVE,
      upVotes: 321,
      downVotes: 43
    },
    {
      _id: '6434124132',
      authorName: 'Michał',
      userVote: UserVote.POSITIVE,
      title: 'Con 2',
      content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      attitude: ArgumentAttitude.NEGATIVE,
      upVotes: 31131,
      downVotes: 3123
    },
  ]);

  constructor(private http: HttpClient) {}

  getArgumentsForDebate(id: string): Observable<Argument[]> {
    return this.dummy.asObservable();
  }

  rateArgument(argumentId: string, userVote: UserVote): void {

  }
}
