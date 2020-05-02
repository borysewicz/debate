import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AddUpdateDebateDto } from '../dto/addUpdateDebate.dto';
import { Debate } from '../dto/debate.dto';

@Injectable({
  providedIn: 'root',
})
export class DebateService {
  private readonly endpoint = 'http://localhost:8080/api/debate';
  private readonly searchEndpoint = 'http://localhost:8080/api/search';
  private readonly coverImageBaseUrl = 'http://localhost:8080/api/debate/cover/';
  
  constructor(private http: HttpClient) {}

  addDebate(debate: AddUpdateDebateDto, cover?: File): Observable<Debate> {
    const formData = new FormData();
    const debateBlob = new Blob([JSON.stringify(debate) as BlobPart], {
      type: 'application/json',
    });
    formData.append('debate', debateBlob);
    if (cover) {
      formData.append('img', cover);
    }
    return this.http.post<Debate>(this.endpoint + '/add', formData);
  }

  getCoverImageUrl(id: string): string {
    return this.coverImageBaseUrl + id;
  }

  getDebateById(id: string): Observable<Debate> {
    return this.http.get<Debate>(`${this.endpoint}/${id}`);
  }

  getPopularDebates(limit: number, page: number): Observable<Debate[]> {
    return this.getDebates('popular', limit , page);
  }

  getNewDebates(limit: number, page: number): Observable<Debate[]> {
    return this.getDebates('new', limit, page);
  }

  getHotDebates(limit: number, page: number): Observable<Debate[]> {
    return this.getDebates('hot', limit, page);
  }

  private getDebates(
    sort: string,
    limit: number,
    page: number
  ): Observable<Debate[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('sort', sort);
    queryParams = queryParams.append('limit', limit.toString());
    queryParams = queryParams.append('page', page.toString());
    return this.http.get<Debate[]>(this.endpoint, { params: queryParams });
  }

  getDebatesByName(name: string): Observable<Debate[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('reqName', name);
    return this.http.get<Debate[]>(`${this.searchEndpoint}/byName`, {
      params: queryParams,
    });
  }

  getDebatesByTags(tags: string[]): Observable<Debate[]> {
    let queryParams = new HttpParams();
    let text = '';
    for (let tag of tags) {
      text += tag + ';';
    }
    queryParams = queryParams.append('Tags', text);
    return this.http.get<Debate[]>(`${this.searchEndpoint}/byTags`, {
      params: queryParams,
    });
  }
}
