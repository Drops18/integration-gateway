import { useEffect, useState } from 'react'

type HelloResponse = {
  message: string
}

function App() {
  const [message, setMessage] = useState<string>('Ładowanie...')
  const [error, setError] = useState<string>('')

  useEffect(() => {
    fetch('http://localhost:8080/api/hello')
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Błąd HTTP: ${response.status}`)
        }
        return response.json() as Promise<HelloResponse>
      })
      .then((data) => {
        setMessage(data.message)
      })
      .catch((err: Error) => {
        setError(err.message)
      })
  }, [])

  return (
    <div style={{ padding: '40px', fontFamily: 'Arial, sans-serif' }}>
      <h1>React + Spring Boot + PostgreSQL TEST</h1>
      {error ? <p>Błąd: {error}</p> : <p>Wartość z backendu: {message}</p>}
    </div>
  )
}

export default App