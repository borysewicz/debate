export interface Argument {
    _id: string;
    title: string;
    content: string;
    attitude: ArgumentAttitude;
    upVotes: number;
    downVotes: number;
}

export enum ArgumentAttitude {
    POSITIVE,
    NEGATIVE
}
