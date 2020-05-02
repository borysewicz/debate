import { UserVote } from './userVote.enum';

export interface Argument {
    _id: string;
    title: string;
    author: string;
    content: string;
    userVote: UserVote;
    attitude: ArgumentAttitude;
    upVotes: number;
    downVotes: number;
    lastEditTime: Date;
}

export enum ArgumentAttitude {
    POSITIVE,
    NEGATIVE
}
