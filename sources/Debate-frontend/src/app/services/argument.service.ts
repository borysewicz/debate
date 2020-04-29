import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Argument } from '../dto/argument.dto';
import { Rating } from '../dto/rating.dto';
import { UserVote } from './../dto/userVote.enum';

@Injectable({
  providedIn: 'root',
})
export class ArgumentService {
  private endpoint = 'http://localhost:8080/api/argument';

  constructor(private http: HttpClient) {}

  getArgumentsForDebate(id: string): Observable<Argument[]> {
    return this.http.get<Argument[]>(this.endpoint);
  }

  rateArgument(id: string, rating: UserVote): Observable<Rating> {
    return this.http.patch<Rating>(`${this.endpoint}/rate/${id}`, rating);
  }
}
