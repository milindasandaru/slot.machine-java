import type { Request, Response } from 'express';
import { analyzeRequest } from '../services/ai.service.ts';
import { Logger } from '../utils/logger.ts';

export const createAnalysis = async (req: Request, res: Response) => {
    try {
        const { message } = req.body;

        if (!message) {
            return res.status(400).json({ error: 'Message is required.' });
        }

        Logger.info(`Analyzing request: ${message.substring(0, 50)}...`);

        const analysis = await analyzeRequest(message);

        // in the future, we might store analysis in DB here

        return res.status(200).json({ analysis });
    } catch (error) {
        Logger.error(`Error in createAnalysis: ${(error as Error).message}`);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
};
