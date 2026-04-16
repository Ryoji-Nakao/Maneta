import { useState, useEffect} from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getArticleById, updateArticle } from "../api/articles";
import type { ArticleRequest } from "../types";

function ArticleEdit() {
    const [title, setTitle] = useState('')
    const [body, setBody] = useState('')
    const [published, setPublished] = useState(true)
    const [tags, setTags] = useState<string[]>([])
    const [tagInput, setTagInput] = useState('')
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)
    const { id } = useParams()
    const navigate = useNavigate()

    useEffect(() => {
        const fetch = async () => {
            const article = await getArticleById(Number(id))
            setTitle(article.title)
            setBody(article.body)
            setPublished(article.published)
            setTags(article.tagNames)
        }
        fetch()
    }, [])

    const handleAddTag = () => {
        if (tagInput === '') return
        if (tags.includes(tagInput)) return
        setTags([...tags, tagInput])
        setTagInput('')
    }

    const handleRemoveTag = (removeTag: string) => {
        setTags(tags.filter(tag => tag !== removeTag))
    }

    const handleSubmit = async () => {
        setLoading(true)
        try {
            await updateArticle(Number(id), { title, body, published, tagNames: tags })
            navigate('/')
        } catch (e) {
            setError('記事の更新に失敗しました。')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="max-w-3xl mx-auto px-4 py-8">
            <div className="bg-white rounded-lg border border-gray-200 p-8">
                <h1 className="text-2xl font-bold text-gray-800 mb-6">記事を編集する</h1>

                {error && (
                    <div className="bg-red-50 border border-red-200 text-red-600 text-sm rounded-lg px-4 py-3 mb-5">
                        {error}
                    </div>
                )}

                <div className="mb-5">
                    <label className="block text-sm font-medium text-gray-700 mb-1">タイトル</label>
                    <input
                        type="text"
                        placeholder="タイトルを入力"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-400"
                    />
                </div>

                <div className="mb-5">
                    <label className="block text-sm font-medium text-gray-700 mb-1">本文</label>
                    <textarea
                        placeholder="本文を入力"
                        value={body}
                        onChange={(e) => setBody(e.target.value)}
                        rows={12}
                        className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-400 resize-y"
                    />
                </div>

                <div className="mb-5">
                    <label className="block text-sm font-medium text-gray-700 mb-2">タグ</label>
                    <div className="flex gap-2 mb-2">
                        <input
                            type="text"
                            placeholder="タグを入力してEnter"
                            value={tagInput}
                            onChange={(e) => setTagInput(e.target.value)}
                            onKeyDown={(e) => e.key === 'Enter' && handleAddTag()}
                            className="flex-1 border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-400"
                        />
                        <button
                            onClick={handleAddTag}
                            className="px-4 py-2 bg-gray-100 text-gray-600 rounded-lg text-sm hover:bg-gray-200 transition-colors"
                        >
                            追加
                        </button>
                    </div>
                    <div className="flex flex-wrap gap-2">
                        {tags.map((tag) => (
                            <span
                                key={tag}
                                className="flex items-center gap-1 bg-green-50 text-green-700 text-xs px-3 py-1 rounded-full border border-green-200"
                            >
                                {tag}
                                <button onClick={() => handleRemoveTag(tag)} className="text-green-500 hover:text-green-700 font-bold ml-1">×</button>
                            </span>
                        ))}
                    </div>
                </div>

                <div className="mb-6 flex items-center gap-2">
                    <input
                        type="checkbox"
                        id="published"
                        checked={published}
                        onChange={(e) => setPublished(e.target.checked)}
                        className="w-4 h-4 accent-green-500"
                    />
                    <label htmlFor="published" className="text-sm text-gray-600">公開する</label>
                </div>

                <div className="flex gap-3">
                    <button
                        onClick={handleSubmit}
                        disabled={loading}
                        className="bg-green-500 text-white px-6 py-2 rounded-lg text-sm font-medium hover:bg-green-600 transition-colors disabled:opacity-50"
                    >
                        {loading ? '更新中...' : '更新する'}
                    </button>
                    <button
                        onClick={() => navigate(`/articles/${id}`)}
                        className="px-6 py-2 text-sm border border-gray-300 rounded-lg text-gray-600 hover:bg-gray-50 transition-colors"
                    >
                        キャンセル
                    </button>
                </div>
            </div>
        </div>
    )
}

export default ArticleEdit
