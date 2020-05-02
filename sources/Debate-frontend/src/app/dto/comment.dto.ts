import { UserVote } from './userVote.enum';

export interface  Comment {
    _id: string;
    content: string;
    creationDate: Date;
    lastEditTime: Date;
    author: string;
    upvotes: number;
    downvotes: number;
    userVote: UserVote;
}
