export interface Debate {
    id: number, 
    title: string;
    description: string;
    imageSrc: string;
    argumentCount: number;
    commentCount: number;
    voteCount: number;
    participantCount: number;
    viewCount: number;
    mainTags: string[];
    allTags: string[];
}