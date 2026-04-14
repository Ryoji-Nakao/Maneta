import { useState, useEffect } from 'react';
import { getArticles } from '../api/articles';
import type { Article } from '../types';
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../store/authContext';

const Home = () => {
    const [articles, setArticles] = useState<Article[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const { username } = useAuth()
    const navigate = useNavigate()

    useEffect(() => {
        const fetch = async () => {
            const article = await getArticles();
            setArticles(article);
            setLoading(false);
        }
        fetch();
    }, []);

    if (loading) {
        return <div>読み込み中...</div>
    }

    return (
        <div>
            <h1>記事一覧</h1>
            {articles.map((article) => (
                <div key={article.id}>
                    <Link to={`/articles/${article.id}`}>
                    <h2>{article.title}</h2>
                    </Link>
                    <p>{article.authorUsername}</p>
                </div>
            ))}
            {username && (
            <button onClick={() => navigate('/articles/new')}>
            新規作成
            </button>
            )}
        </div>
    );
};

export default Home;