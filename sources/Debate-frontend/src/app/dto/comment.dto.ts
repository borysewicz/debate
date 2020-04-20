import { UserVote } from './userVote.enum';

export interface  Comment {
    _id: string;
    publishedAt: Date;
    authorName: string;
    activityId: string;
    userVote: UserVote;
}
