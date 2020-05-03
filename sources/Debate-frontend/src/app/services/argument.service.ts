import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Argument } from '../dto/argument.dto';
import { Rating } from '../dto/rating.dto';
import { ArgumentAttitude } from './../dto/argument.dto';
import { UserVote } from './../dto/userVote.enum';

@Injectable({
  providedIn: 'root',
})
export class ArgumentService {
  private endpoint = 'http://localhost:8080/api/argument';

  constructor(private http: HttpClient) {}

  getArgumentsForDebate(id: string): Observable<Argument[]> {
    const params = new HttpParams()
      .set('debate', id)
      .set('limit', '10')
      .set('page', '0');
    return this.http
      .get<Argument[]>(this.endpoint, { params })
      .pipe(map(args => {
        args.forEach(element => {
          element.attitude = ArgumentAttitude[element.attitude.toString()];
          element.userVote = UserVote[element.userVote.toString()];
        });
        return args;
      }));
  }

  rateArgument(id: string, rating: UserVote): Observable<Rating> {
    return this.http.patch<Rating>(`${this.endpoint}/rate/${id}`, { vote: UserVote[rating] });
  }
}
