import { Observable, BehaviorSubject } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Debate } from '../dto/debate.dto';
import { AddUpdateDebateDto } from '../dto/addUpdateDebate.dto';

@Injectable({
  providedIn: 'root',
})
export class DebateService {

  constructor(private http: HttpClient) {}
  private readonly endpoint = 'http://localhost:8080/api/debate';
  private readonly coverImageBaseUrl = 'http://localhost:8080/api/debate/cover/';

  private sub = new BehaviorSubject<Debate>({
    _id: '12313131',
    title: 'Czy wybory powinny się odbyć w maju?',
    content: 'Wiele kontrowersji budzi pomysł przeprowadzenia wyborów prezydenckich w maju 2020. Koalicja rządząca chce wyborów mimo niechęci Polek i Polaków.',
    argumentCount: 5,
    commentCount: 3,
    voteCount: 13213,
    participantCount: 4,
    viewCount: 880,
    mainTags: ['Polityka', 'Wybory', 'COVID-19'],
    allTags: ['Polityka', 'Wybory', 'COVID-19', 'PiS', 'Prezydent']
  });

  addDebate(debate: AddUpdateDebateDto, cover?: File): Observable<Debate> {
    const formData = new FormData();
    const debateBlob = new Blob([JSON.stringify(debate) as BlobPart], { type: 'application/json'});
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
    return this.sub.asObservable() || this.http.get<Debate>(this.endpoint + '/' + id);
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

  private getDebates(sort: string, limit: number, page: number): Observable<Debate[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('sort', sort);
    queryParams = queryParams.append('limit', limit.toString());
    queryParams = queryParams.append('page', page.toString());
    return this.http.get<Debate[]>(this.endpoint, { params: queryParams});
  }

}
