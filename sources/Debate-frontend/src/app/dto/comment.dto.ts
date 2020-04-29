import { UserVote } from './userVote.enum';

export interface  Comment {
    _id: string;
    content: string;
    publishedAt: Date;
    authorName: string;
    activityId: string;
    upVotes: number;
    downVotes: number;
    userVote: UserVote;
}
