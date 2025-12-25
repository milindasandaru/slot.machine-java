const express = require("express");
const cors = require("cors");
require("dotenv").config();

// Import our AI functions
const { extractSearchIntents, generateStylistResponse } = require("./gemini");

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware
app.use(cors()); // Allows frontend to talk to backend
app.use(express.json()); // Allows us to read JSON inputs

// ROUTE 1: The "Filter" Step
// Frontend sends text -> Backend returns Search JSON
app.post("/api/search", async (req, res) => {
  const { query } = req.body;
  
  if (!query) {
    return res.status(400).json({ error: "Query is required" });
  }
  const now = new Date().toISOString();
  console.log(`Request /api/search at ${now}`);
  console.log("Analyzing user intent for:", query);
  const searchFilters = await extractSearchIntents(query);
  console.log("Extracted filters:", searchFilters);
  
  res.json(searchFilters);
});

// ROUTE 2: The "Stylist" Step
// Frontend sends User Query + Filtered Products -> Backend returns Chat Message
app.post("/api/chat", async (req, res) => {
  const { query, products } = req.body;

  if (!query || !products) {
    return res.status(400).json({ error: "Query and Products are required" });
  }

  const now = new Date().toISOString();
  console.log(`Request /api/chat at ${now}`);
  console.log("Generating stylist response...");
  const aiResponse = await generateStylistResponse(query, products);
  
  res.json({ message: aiResponse });
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});