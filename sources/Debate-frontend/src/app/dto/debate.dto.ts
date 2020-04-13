export interface Debate {
  _id: string;
  title: string;
  content: string;
  argumentCount: number;
  commentCount: number;
  voteCount: number;
  participantCount: number;
  viewCount: number;
  mainTags: string[];
  allTags: string[];
}
