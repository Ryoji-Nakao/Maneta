export type Article = {
    id: number;
    title: string;
    body: string;
    published: boolean;
    createdAt: string;
    updatedAt: string;
    authorUsername: string;
    tagNames: string[];
}

export type ArticleRequest = {
    title: string;
    body: string;
    published: boolean;
    tagNames: string[];
}