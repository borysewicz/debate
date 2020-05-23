import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Argument } from '../dto/argument.dto';
import { Rating } from '../dto/rating.dto';
import { environment } from './../../environments/environment';
import { AddUpdateArgument } from './../dto/addUpdateArgument.dto';
import { ArgumentAttitude } from './../dto/argument.dto';
import { UserVote } from './../dto/userVote.enum';

@Injectable({
  providedIn: 'root',
})
export class ArgumentService {
  private readonly endpoint = environment.api + '/argument';

  constructor(private http: HttpClient) {}

  getArgumentsForDebate(debateId: string, limit: number, page: number): Observable<Argument[]> {
    const params = new HttpParams()
      .set('debate', debateId)
      .set('limit', limit.toString())
      .set('page', page.toString());
    return this.http
      .get<Argument[]>(this.endpoint, { params })
      .pipe(
        map((args) => {
          args.forEach((element) => this.mapArgumentResponse(element));
          return args;
        })
      );
  }

  addArgument(argument: AddUpdateArgument): Observable<Argument> {
    return this.http
      .post<Argument>(`${this.endpoint}/add`, argument)
      .pipe(map(this.mapArgumentResponse));
  }

  rateArgument(id: string, rating: UserVote): Observable<Rating> {
    return this.http.patch<Rating>(`${this.endpoint}/rate/${id}`, {
      vote: UserVote[rating],
    });
  }

  private mapArgumentResponse(argument: Argument): Argument {
    argument.attitude = ArgumentAttitude[argument.attitude.toString()];
    argument.userVote = UserVote[argument.userVote.toString()];
    return argument;
  }
}
