import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../store/authContext'

const Header = () => {
  const { username, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/')
  }

  return (
    <header className="bg-white border-b border-gray-200 sticky top-0 z-10 shadow-sm">
      <div className="max-w-4xl mx-auto px-4 py-3 flex items-center justify-between">
        <Link to="/" className="text-xl font-bold text-green-600 tracking-tight">
          Maneta
        </Link>
        <nav className="flex items-center gap-4">
          {username ? (
            <>
              <span className="text-sm text-gray-500">{username}</span>
              <Link
                to="/articles/new"
                className="bg-green-500 text-white px-4 py-1.5 rounded-full text-sm font-medium hover:bg-green-600 transition-colors"
              >
                投稿する
              </Link>
              <button
                onClick={handleLogout}
                className="text-sm text-gray-400 hover:text-gray-600 transition-colors"
              >
                ログアウト
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="text-sm text-gray-600 hover:text-gray-900 transition-colors">
                ログイン
              </Link>
              <Link
                to="/register"
                className="bg-green-500 text-white px-4 py-1.5 rounded-full text-sm font-medium hover:bg-green-600 transition-colors"
              >
                新規登録
              </Link>
            </>
          )}
        </nav>
      </div>
    </header>
  )
}

export default Header
