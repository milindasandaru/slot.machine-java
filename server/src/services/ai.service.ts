import { GoogleGenerativeAI } from "@google/generative-ai";
import dotenv from "dotenv";

dotenv.config();

// initialize the Google Generative AI client
const genAI = new GoogleGenerativeAI(process.env.GEMINI_API_KEY as string);
// const model = genAI.getGenerativeModel({ model: "gemini-1.5-flash" });
// New:
const model = genAI.getGenerativeModel({ model: "gemini-2.5-flash" });

// The system prompt to guide the AI's behavior
const SYSTEM_PROMPT = `
You are a Senior Technical Project Manager. 
Your goal is to extract structured requirements from a client's vague request.

INPUT: A raw text string from a non-technical client.
OUTPUT: A clean JSON object (No Markdown).

RULES:
1. Identify the "Core Intent" (e.g., E-commerce, Blog, SaaS).
2. Generate a list of "Must-Have" features based on industry standards.
3. Identify "Missing Info" (Questions the freelancer MUST ask).
4. Do NOT estimate prices yet. Focus on Scope.

JSON STRUCTURE:
{
  "projectType": "String",
  "summary": "String (Professional description)",
  "features": [
    { "title": "Feature Name", "complexity": "Low/Mid/High", "description": "Why it's needed" }
  ],
  "clarificationQuestions": ["Question 1", "Question 2"]
}
`;

export const analyzeRequest = async (userInput: string) => {
    try {
        const prompt = `${SYSTEM_PROMPT}\n\nCLIENT SAYS: ${userInput}`;

        const result = await model.generateContent(prompt);
        const response = result.response.text();

        //clean up if gemini returns markdown code block
        const cleanJson = response.replace(/```json|```/g, '').trim();

        return JSON.parse(cleanJson);

    } catch (error) {
        console.error("AI Service Error:", error);
        throw new Error("Failed to analyze project scope.");
    }

};