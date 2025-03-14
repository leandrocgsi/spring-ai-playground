import React, {useState} from 'react';
import './App.css';
import TalkWithAI from './pages/Chat/TalkWithAI'
import RecipeGenerator from './pages/Recipe/RecipeGenerator';
import ImageGenerator from './pages/Image/ImageGenerator';

function App() {

  const [activeTab, setActiveTab] = useState('ask-ai');

  const handleTabChange = (tab) => {
    //alert(tab)
    setActiveTab(tab);
  };

  return (
    <div className="App">
      <button
        className={activeTab === 'ask-ai' ? 'active' : ''}
        onClick={() => handleTabChange('ask-ai')}>
          Talk with AI
      </button>
      <button
        className={activeTab === 'recipe-generator' ? 'active' : ''}
        onClick={() => handleTabChange('recipe-generator')}>
          Generate Recipe
      </button>
      <button
        className={activeTab === 'image-generator' ? 'active' : ''}
        onClick={() => handleTabChange('image-generator')}>
          Generate Image
      </button>

      <div>
        {activeTab === 'ask-ai' && <TalkWithAI/>}
        {activeTab === 'recipe-generator' && <RecipeGenerator/>}
        {activeTab === 'image-generator' && <ImageGenerator/>}
      </div>
    </div>
  );
}

export default App;