import React, { useState } from "react";

import api from '../../services/api'

function ImageGenerator() {

    const [prompt, setPrompt] = useState('');
    const [imageUrls, setImageUrls] = useState([]);

    const generateImage = async () => {
        try {           
            const response = await api.get(`generate-image`, {
                params: { prompt }
            });

            const urls = await response.data;
            console.log(urls);
            setImageUrls(urls);
        } catch (error) {
            console.error("Error generating image : ", error)
        }
    }
    
    return (
        <div>
            <h2>Generate Image</h2>
            <input
                type="text"
                value={prompt}
                onChange={(e) => setPrompt(e.target.value)}
                placeholder="Enter prompt for image"
            />
            <button onClick={generateImage}>Generate Image</button>

            <div className="image-grid">
                {imageUrls.map((url, index) => (
                    <img key={index} src={url} alt={`Generated ${index}`} />
                ))}
                {[...Array(4 - imageUrls.length)].map((_, index) => (
                    <div key={index + imageUrls.length}
                        className="empty-image-slot"></div>
                    ))}
            </div>
        </div>   
    );
}

export default ImageGenerator;