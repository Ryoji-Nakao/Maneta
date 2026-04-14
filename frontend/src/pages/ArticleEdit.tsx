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
            await updateArticle(Number(id), {
                title,
                body,
                published,
                tagNames: tags,
            })
            navigate('/')
        } catch (e) {
            setError('記事の更新に失敗しました。')
        } finally {
            setLoading(false)
        }
    }

    return (
    <div>
        <h1 className="text-2xl font-bold mb-6">記事更新</h1>
        {error && <p className="text-red-500 mb-4">{error}</p>}

        <input
        type="text"
        placeholder="タイトル入力"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        className="w-full border p-2 rounded mb-4"
      />

      <textarea
        placeholder="本文入力"
        value={body}
        onChange={(e) => setBody(e.target.value)}
        className="w-full border p-2 rounded mb-4"
      />

      <input
        type="checkbox"
        checked={published}
        onChange={(e) => setPublished(e.target.checked)}
      />

      <input
        type="text"
        placeholder="タグ入力"
        value={tagInput}
        onChange={(e) => setTagInput(e.target.value)}
      />
      <button onClick={handleAddTag}>追加</button>

      {tags.map((tag) => (
        <span key={tag}>
          {tag}
          <button onClick={() => handleRemoveTag(tag)}>×</button>
        </span>
      ))}

      <button
        onClick={handleSubmit}
        className="w-full bg-blue-500 text-white p-2 rounded"
      >
        更新
      </button>
    </div>
    )
}

export default ArticleEdit