import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Home from './pages/Home'
import ArticleDetail from './pages/ArticleDetail'
import ArticleCreate from './pages/ArticleCreate'
import ArticleEdit from './pages/ArticleEdit'
import Header from './components/Header'

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/articles/new" element={<ArticleCreate />} />
        <Route path="/articles/:id" element={<ArticleDetail />} />
        <Route path="/articles/:id/edit" element={<ArticleEdit />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App