import { useState, useEffect } from 'react';
import { getArticles } from '../api/articles';
import type { Article } from '../types';
import { Link } from 'react-router-dom'

const Home = () => {
    const [articles, setArticles] = useState<Article[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [tag, setTag] = useState('')

    useEffect(() => {
        const fetch = async () => {
            const article = await getArticles(tag);
            setArticles(article);
            setLoading(false);
        }
        fetch();
    }, [tag]);

    if (loading) {
        return (
            <div className="flex justify-center items-center min-h-screen">
                <p className="text-gray-400 text-sm">読み込み中...</p>
            </div>
        )
    }

    return (
        <div className="max-w-4xl mx-auto px-4 py-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-gray-800">記事一覧</h1>
            </div>
            <input
                type="text"
                placeholder="タグで検索"
                value={tag}
                onChange={(e) => setTag(e.target.value)}
                className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-400 mb-6"
            />
            {tag && (
            <button
                onClick={() => setTag('')}
                className="text-xs text-gray-400 hover:text-gray-600 ml-2"
            >
             ✕ 絞り込み解除
            </button>
            )}

            <div className="space-y-4">
                {articles.map((article) => (
                    <div
                        key={article.id}
                        className="bg-white rounded-lg border border-gray-200 p-5 hover:shadow-md transition-shadow"
                    >
                        <Link to={`/articles/${article.id}`}>
                            <h2 className="text-lg font-semibold text-gray-800 hover:text-green-600 transition-colors mb-2">
                                {article.title}
                            </h2>
                        </Link>
                        <p className="text-sm text-gray-400">@{article.authorUsername}</p>

                        <div className="flex flex-wrap gap-2 mt-3">
                            {article.tagNames.map((tag) => (
                            <span
                                key={tag}
                                onClick={() => setTag(tag)}
                                className="bg-green-50 text-green-700 text-xs px-3 py-1 rounded-full border border-green-200 cursor-pointer hover:bg-green-100 transition-colors"
                            >
                            {tag}
                            </span>
                            ))}
                        </div>
                    </div>
                ))}
            </div>

            

            {articles.length === 0 && (
                <div className="text-center py-20 text-gray-400">
                    <p>記事がまだありません</p>
                </div>
            )}
        </div>
    );
};

export default Home;
