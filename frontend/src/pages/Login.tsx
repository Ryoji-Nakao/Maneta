import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../store/authContext'
import axiosInstance from '../api/axiosConfig'

function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const { login } = useAuth()
  const navigate = useNavigate()

  const handleLogin = async () => {
    setLoading(true)
    try {
      const response = await axiosInstance.post('/api/auth/login', {
        username,
        password,
      })
      login(response.data.token)
      navigate('/')
    } catch (e) {
      setError('ユーザー名またはパスワードが違います')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="max-w-md mx-auto mt-20 p-8 border rounded-lg">
      <h1 className="text-2xl font-bold mb-6">ログイン</h1>

      {error && <p className="text-red-500 mb-4">{error}</p>}

      <input
        type="text"
        placeholder="ユーザー名"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        className="w-full border p-2 rounded mb-4"
      />
      <input
        type="password"
        placeholder="パスワード"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className="w-full border p-2 rounded mb-4"
      />
      <button
        onClick={handleLogin}
        className="w-full bg-blue-500 text-white p-2 rounded"
      >
        ログイン
      </button>
    </div>
  )
}

export default Login