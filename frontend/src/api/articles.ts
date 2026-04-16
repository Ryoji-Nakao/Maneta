import axiosInstance from './axiosConfig';
import type { Article, ArticleRequest, Comment} from '../types';

export const getArticles = async (tag?: string): Promise<Article[]> => {
  const response = await axiosInstance.get('/api/articles', {
    params: tag ? { tag } : {}
  })
  return response.data
}

export const getArticleById = async (id: number): Promise<Article> => {
    const response = await axiosInstance.get(`/api/articles/${id}`)
    return response.data
}

export const createArticle = async (data: ArticleRequest): Promise<Article> => {
    const response = await axiosInstance.post(`/api/articles`, data)
    return response.data
}

export const updateArticle = async (id: number, data: ArticleRequest): Promise<Article> => {
    const response = await axiosInstance.put(`/api/articles/${id}`, data)
    return response.data
}

export const deleteArticle = async (id: number): Promise<void> => {
    await axiosInstance.delete(`/api/articles/${id}`)
}

export const likeArticle = async (id: number): Promise<void> => {
    await axiosInstance.post(`/api/articles/${id}/likes`)
}

export const unlikeArticle = async (id: number): Promise<void> => {
    await axiosInstance.delete(`/api/articles/${id}/likes`)
}

export const getComments = async (id: number): Promise<Comment[]> => {
    const response = await axiosInstance.get(`/api/articles/${id}/comments`)
    return response.data
}

export const postComment = async (id: number, body: string): Promise<Comment[]> => {
    const response = await axiosInstance.post(`/api/articles/${id}/comments`, { body } )
    return response.data
}