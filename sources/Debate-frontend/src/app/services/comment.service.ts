import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Comment } from '../dto/comment.dto';
import { UserVote } from '../dto/userVote.enum';
import { Rating } from './../dto/rating.dto';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  constructor(private http: HttpClient) {}
  private readonly endpoint = 'http://localhost:8080/api/comment';

  getCommentsForArgument(id: string): Observable<Comment[]> {
    return this.getComments(id);
  }

  getCommentsForDebate(id: string): Observable<Comment[]> {
    return this.getComments(id);
  }

  private getComments(id: string): Observable<Comment[]> {
    return this.http
      .get<Comment[]>(`${this.endpoint}/activity/${id}`)
      .pipe(map((args) => {
        args.forEach(element => {
          element.userVote = UserVote[element.userVote.toString()];
        });
        return args;
      }));
  }

  rateComment(id: string, vote: UserVote): Observable<Rating> {
    return this.http.patch<Rating>(`${this.endpoint}/rate/${id}`, { vote: UserVote[vote] });
  }
}
