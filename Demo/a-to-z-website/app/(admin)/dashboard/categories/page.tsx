"use client"
import { useState, useEffect } from 'react'
import { supabase } from '@/lib/supabase'

export default function CategoryManager() {
  const [name, setName] = useState('')
  const [categories, setCategories] = useState<any[]>([])

  // 1. Fetch categories from Supabase
  const fetchCategories = async () => {
    const { data } = await supabase.from('categories').select('*').order('created_at', { ascending: false })
    if (data) setCategories(data)
  }

  // 2. Load them when the page opens
  useEffect(() => {
    fetchCategories()
  }, [])

  // 3. Add a new category
  const handleAdd = async () => {
    if (!name.trim()) return // Don't save empty names!
    
    const { error } = await supabase
      .from('categories')
      .insert([{ 
        name, 
        slug: name.toLowerCase().trim().replace(/\s+/g, '-') 
      }])
    
    if (error) {
      alert(error.message)
    } else {
      setName('') // Clear the input
      fetchCategories() // Refresh the list
    }
  }

  return (
    <div className="p-10 max-w-2xl mx-auto text-white">
      <h1 className="text-3xl font-bold mb-8">Category Management</h1>
      
      {/* Input Section */}
      <div className="flex gap-4 mb-12">
        <input 
          className="bg-zinc-900 border border-zinc-700 p-3 rounded-lg flex-1 focus:ring-2 focus:ring-blue-500 outline-none text-white"
          value={name} 
          onChange={(e) => setName(e.target.value)} 
          placeholder="New Category (e.g. Artifacts, Trays)"
        />
        <button 
          onClick={handleAdd} 
          className="bg-blue-600 hover:bg-blue-700 px-6 py-3 rounded-lg font-bold transition-all"
        >
          Add
        </button>
      </div>

      {/* Display Section */}
      <div className="space-y-3">
        <h2 className="text-xl font-semibold mb-4 text-zinc-400">Current Categories</h2>
        {categories.length === 0 && <p className="text-zinc-600 italic">No categories added yet.</p>}
        {categories.map((cat) => (
          <div key={cat.id} className="bg-zinc-900/50 p-4 rounded-xl border border-zinc-800 flex justify-between items-center">
            <span className="font-medium text-lg">{cat.name}</span>
            <span className="text-zinc-500 font-mono text-sm">/{cat.slug}</span>
          </div>
        ))}
      </div>
    </div>
  )
}