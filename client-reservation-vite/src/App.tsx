import './App.css'
import Form from './components/Form.tsx'
import Table from './components/Table.tsx'

function App(){

  return (
    <div className='flex bg-slate-800'>
      <div className='bg-gray-900 m-3 rounded-xl p-3'>
        <Form/>
      </div>
      <div className='bg-blue-950 m-3 b-3 rounded-xl'>
        <Table/>
      </div>
    </div>
  )
}

export default App
