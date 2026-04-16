import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getArticleById, deleteArticle, likeArticle, unlikeArticle, getComments, postComment} from "../api/articles";
import { useAuth } from "../store/authContext";
import type { Article, Comment } from "../types";

const ArticleDetail = () => {
    const [article, setArticle] = useState<Article | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const { id } = useParams();
    const { username } = useAuth()
    const navigate = useNavigate()
    const [liked, setLiked] = useState(false)
    const [likeCount, setLikeCount] = useState(0)
    const [comments, setComments] = useState<Comment[]>([])
    const [commentBody, setCommentBody] = useState('')

    const handleDelete = async () => {
        if (!window.confirm('本当に削除しますか？')) return
        await deleteArticle(Number(id))
        navigate('/')
    }

    const handleLike = async () => {
        if (liked) {
            await unlikeArticle(Number(id))
            setLiked(false)
            setLikeCount(likeCount - 1)
        } else {
            await likeArticle(Number(id))
            setLiked(true)
            setLikeCount(likeCount + 1)
        }
    }

    const handlePostComment = async () => {
        await postComment(Number(id), commentBody)
        const updated = await getComments(Number(id))
        setComments(updated)
        setCommentBody('')
    }

    useEffect(() => {
        const fetch = async () => {
            const article = await getArticleById(Number(id))
            const comments = await getComments(Number(id))
            setArticle(article)
            setComments(comments)
            setLiked(article.likedByMe)
            setLikeCount(article.likeCount)
            setLoading(false)
        }
        fetch();
    }, [])

    if (loading) {
        return (
            <div className="flex justify-center items-center min-h-screen">
                <p className="text-gray-400 text-sm">読み込み中...</p>
            </div>
        )
    }

    if (!article) {
        return (
            <div className="flex justify-center items-center min-h-screen">
                <p className="text-gray-500">記事が見つかりません</p>
            </div>
        )
    }

    return (
        <div className="max-w-3xl mx-auto px-4 py-8">
            <div className="bg-white rounded-lg border border-gray-200 p-8">

                {/* タイトル + 編集・削除 */}
                <div className="flex items-start justify-between mb-4">
                    <h1 className="text-3xl font-bold text-gray-900">{article.title}</h1>
                    {username === article.authorUsername && (
                        <div className="flex gap-2 ml-4">
                            <button
                                onClick={() => navigate(`/articles/${article.id}/edit`)}
                                className="px-3 py-1.5 text-sm border border-gray-300 rounded-lg text-gray-600 hover:bg-gray-50 transition-colors"
                            >
                                編集
                            </button>
                            <button
                                onClick={handleDelete}
                                className="px-3 py-1.5 text-sm border border-red-300 rounded-lg text-red-500 hover:bg-red-50 transition-colors"
                            >
                                削除
                            </button>
                        </div>
                    )}
                </div>

                {/* メタ情報 */}
                <div className="flex items-center gap-4 text-sm text-gray-400 mb-6 pb-6 border-b border-gray-100">
                    <span>@{article.authorUsername}</span>
                    <span>投稿日: {new Date(article.createdAt).toLocaleDateString('ja-JP')}</span>
                    {article.createdAt !== article.updatedAt && (
                        <span>更新日: {new Date(article.updatedAt).toLocaleDateString('ja-JP')}</span>
                    )}
                </div>

                {/* 本文 */}
                <div className="text-gray-700 leading-relaxed whitespace-pre-wrap mb-8">
                    {article.body}
                </div>

                {/* タグ */}
                {article.tagNames.length > 0 && (
                    <div className="flex flex-wrap gap-2 mb-6">
                        {article.tagNames.map((tag) => (
                            <span
                                key={tag}
                                className="bg-green-50 text-green-700 text-xs px-3 py-1 rounded-full border border-green-200"
                            >
                                {tag}
                            </span>
                        ))}
                    </div>
                )}

                {/* いいねボタン */}
                <button
                    onClick={handleLike}
                    className={`flex items-center gap-1.5 px-4 py-2 rounded-full border text-sm font-medium transition-colors ${
                        liked
                            ? 'bg-red-50 border-red-300 text-red-500'
                            : 'bg-white border-gray-300 text-gray-400 hover:border-red-300 hover:text-red-400'
                    }`}
                >
                    {liked ? '♥' : '♡'} {likeCount}
                </button>

                {/* コメントセクション */}
                <div className="mt-8 pt-8 border-t border-gray-100">
                    <h2 className="text-lg font-semibold text-gray-700 mb-4">コメント（{comments.length}）</h2>

                    {/* コメント一覧 */}
                    <div className="space-y-4 mb-6">
                        {comments.length === 0 && (
                            <p className="text-sm text-gray-400">まだコメントはありません</p>
                        )}
                        {comments.map((comment) => (
                            <div key={comment.id} className="bg-gray-50 rounded-lg p-4">
                                <div className="flex items-center gap-2 mb-2">
                                    <span className="text-sm font-medium text-gray-700">@{comment.authorUsername}</span>
                                    <span className="text-xs text-gray-400">{new Date(comment.createdAt).toLocaleDateString('ja-JP')}</span>
                                </div>
                                <p className="text-sm text-gray-600 whitespace-pre-wrap">{comment.body}</p>
                            </div>
                        ))}
                    </div>

                    {/* 投稿フォーム（ログイン中のみ） */}
                    {username && (
                        <div>
                            <textarea
                                value={commentBody}
                                onChange={(e) => setCommentBody(e.target.value)}
                                placeholder="コメントを入力"
                                rows={3}
                                className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-400 resize-none mb-2"
                            />
                            <button
                                onClick={handlePostComment}
                                className="bg-green-500 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-green-600 transition-colors"
                            >
                                投稿する
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default ArticleDetail;
