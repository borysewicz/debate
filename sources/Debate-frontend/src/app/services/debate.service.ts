import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Debate } from '../dto/debate.dto';

@Injectable({
  providedIn: 'root',
})
export class DebateService {
  private endpoint = 'https://localhost:80/api/debate';

  constructor(private http: HttpClient) {}

  getPopularDebates(limit: number, page: number): Observable<Debate[]> {
    return this.getDebates('popular', limit ,page);
  }

  getNewDebates(limit: number, page: number): Observable<Debate[]> {
    return this.getDebates('new', limit, page);
  }

  getHotDebates(limit: number, page: number): Observable<Debate[]> {
    return this.getDebates('hot', limit, page);
  }

  private getDebates(sort: string, limit: number, page: number): Observable<Debate[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('sort', sort);
    queryParams = queryParams.append('limit', limit.toString());
    queryParams = queryParams.append('page', page.toString());
    return this.http.get<Debate[]>(this.endpoint, { params: queryParams});
  }

}
