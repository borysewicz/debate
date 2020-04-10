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
    let queryParams = new HttpParams();
    queryParams = queryParams.append('sort', 'popular');
    queryParams = queryParams.append('limit', limit.toString());
    queryParams = queryParams.append('page', page.toString());
    return this.http.get<Debate[]>(this.endpoint, { params: queryParams});
  }

  getNewDebates(limit: number, page: number): Observable<Debate[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('sort', 'new');
    queryParams = queryParams.append('limit', limit.toString());
    queryParams = queryParams.append('page', page.toString());
    return this.http.get<Debate[]>(this.endpoint, { params: queryParams});
  }

  getHotDebates(limit: number, page: number): Observable<Debate[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('sort', 'hot');
    queryParams = queryParams.append('limit', limit.toString());
    queryParams = queryParams.append('page', page.toString());
    return this.http.get<Debate[]>(this.endpoint, { params: queryParams});
  }
}
