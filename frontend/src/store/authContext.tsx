import { createContext, useContext, useState, type ReactNode  } from 'react'

type AuthContextType = {
  token: string | null
  username: string | null
  login: (token: string) => void
  logout: () => void
}

const AuthContext = createContext<AuthContextType | null>(null)

export function AuthProvider({ children }: { children: ReactNode }) {

  const decodeToken = (token: string): string => {
  const payload = token.split('.')[1]
  const decoded = decodeURIComponent(
    atob(payload)
      .split('')
      .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
      .join('')
  )
  return JSON.parse(decoded).sub
}
  
  const [token, setToken] = useState<string | null>(
    localStorage.getItem('token')
  )

  const [username, setUsername] = useState<string | null>(
  localStorage.getItem('username')
)

  const login = (token: string) => {
    localStorage.setItem('token', token)
    setToken(token)
    const name =  decodeToken(token)
    localStorage.setItem('username', name)
    setUsername(name)
  }

  const logout = () => {
    localStorage.removeItem('token')
    setToken(null)
    localStorage.removeItem('username')
    setUsername(null)
  }


  return (
    <AuthContext.Provider value={{ token, username, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  const context = useContext(AuthContext)
  if (!context) throw new Error('AuthProvider の外で useAuth が使われています')
  return context
}