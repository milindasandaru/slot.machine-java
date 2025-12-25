const axios = require('axios');
require("dotenv").config();

const API_KEY = process.env.GEMINI_API_KEY;

async function listModels() {
  console.log("Checking available models for your API Key...");
  try {
    const response = await axios.get(
      `https://generativelanguage.googleapis.com/v1beta/models?key=${API_KEY}`
    );
    
    const models = response.data.models;
    console.log("\n✅ SUCCESS! Here are the models you can use:");
    console.log("---------------------------------------------");
    models.forEach(model => {
      if (model.supportedGenerationMethods.includes("generateContent")) {
        console.log(`Model Name: ${model.name}`);
      }
    });
    console.log("---------------------------------------------");
    
  } catch (error) {
    console.error("❌ Error listing models:", error.response ? error.response.data : error.message);
  }
}

listModels();