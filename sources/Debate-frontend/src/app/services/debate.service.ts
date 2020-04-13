import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Debate } from '../dto/debate.dto';
import { AddUpdateDebateDto } from '../dto/addUpdateDebate.dto';

@Injectable({
  providedIn: 'root',
})
export class DebateService {
  private endpoint = 'http://localhost:8080/api/debate';

  constructor(private http: HttpClient) {}

  addDebate(debate: AddUpdateDebateDto, cover?: File): Observable<Debate>{
    const formData = new FormData();
    const debateBlob = new Blob([JSON.stringify(debate) as BlobPart],{ type: "application/json"});
    formData.append("debate", debateBlob);
    if (cover){
      formData.append("img",cover);
    }
    return this.http.post<Debate>(this.endpoint + "/add", formData);
  }

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