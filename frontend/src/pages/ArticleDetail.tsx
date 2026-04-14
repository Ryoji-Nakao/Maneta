import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getArticleById, deleteArticle} from "../api/articles";
import { useAuth } from "../store/authContext";
import type { Article } from "../types";

const ArticleDetail = () => {
    const [article, setArticle] = useState<Article | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const { id } = useParams();
    const { username } = useAuth()
    const navigate = useNavigate()

    const handleDelete = async () => {
        await deleteArticle(Number(id))
        navigate('/')
    }

    useEffect(() => {
        const fetch = async () => {
                    const article = await getArticleById(Number(id));
                    setArticle(article);
                    setLoading(false);
                }
                fetch();
    }, [])

    if (loading) {
        return <div>読み込み中...</div>
    }

    if (!article) {
        return <div>記事が見つかりません</div>
    }

    return (
        <div>
            <h1>記事詳細</h1>
            <div>
                <h2>{article.title}</h2>
                <p>投稿者:{article.authorUsername}</p>
                <p>投稿日:{article.createdAt}</p>
                {article.createdAt !== article.updatedAt && (
                <p>更新日: {article.updatedAt}</p>
                )}
                <p>{article.body}</p>

                {username === article.authorUsername && (
                <div>
                <button onClick={() => navigate(`/articles/${article.id}/edit`)}>
                 編集
                </button>

                <button onClick={handleDelete}>
                 削除
                </button>
                </div>
)} 
            </div>
        </div>
    );
}

export default ArticleDetail;