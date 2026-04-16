export type Article = {
    id: number;
    title: string;
    body: string;
    published: boolean;
    createdAt: string;
    updatedAt: string;
    authorUsername: string;
    tagNames: string[];
    likeCount: number;
    likedByMe: boolean;
}

export type ArticleRequest = {
    title: string;
    body: string;
    published: boolean;
    tagNames: string[];
}

export type Comment = {
    id: number;
    body: string;
    authorUsername: string;
    createdAt: string;
}