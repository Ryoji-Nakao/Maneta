import axiosInstance from './axiosConfig';
import type { Article, ArticleRequest } from '../types';

export const getArticles = async (): Promise<Article[]> => {
  const response = await axiosInstance.get('/api/articles')
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