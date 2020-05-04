import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Comment } from '../dto/comment.dto';
import { UserVote } from '../dto/userVote.enum';
import { environment } from './../../environments/environment';
import { Rating } from './../dto/rating.dto';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private readonly commentEndpoint = environment.api + '/comment';
  private readonly debateEndpoint = environment.api + '/debate';
  private readonly argumentEndpoint = environment.api + '/argument';

  constructor(private http: HttpClient) {}

  getCommentsForArgument(id: string): Observable<Comment[]> {
    return this.http
      .get<Comment[]>(`${this.argumentEndpoint}/comments/${id}`)
      .pipe(
        map((args) => {
          args.forEach((element) => this.mapCommentResponse(element));
          return args;
        })
      );
  }

  getCommentsForDebate(id: string): Observable<Comment[]> {
    return this.http
      .get<Comment[]>(`${this.debateEndpoint}/comments/${id}`)
      .pipe(
        map((args) => {
          args.forEach((element) => this.mapCommentResponse(element));
          return args;
        })
      );
  }

  rateComment(id: string, vote: UserVote): Observable<Rating> {
    return this.http.patch<Rating>(`${this.commentEndpoint}/rate/${id}`, {
      vote: UserVote[vote],
    });
  }

  addDebateComment(debateId: string, comment: string): Observable<Comment> {
    return this.http
      .post<Comment>(`${this.commentEndpoint}/addOnDebate`, {
        parentActivityId: debateId,
        content: comment,
      })
      .pipe(map(this.mapCommentResponse));
  }

  addArgumentComment(argumentId: string, comment: string): Observable<Comment> {
    return this.http
      .post<Comment>(`${this.commentEndpoint}/addOnArgument`, {
        parentActivityId: argumentId,
        content: comment,
      })
      .pipe(map(this.mapCommentResponse));
  }

  private mapCommentResponse(comment: Comment): Comment {
    comment.userVote = UserVote[comment.userVote.toString()];
    return comment;
  }
}
