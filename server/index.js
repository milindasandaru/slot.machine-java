import express from 'express';
import cors from 'cors';
import helmet from 'helmet';
import morgan from 'morgan';
import dotenv from 'dotenv';
import { Logger } from './utils/logger';

dotenv.config();

const app = express();
const PORT = process.env.PORT || 5000;

// 1. Security Headers
app.use(helmet());

// 2. CORS (Strict in production, open in dev)
app.use(cors());

// 3. Body Parsing
app.use(express.json());

// 4. Logging Middleware (Connects Morgan to Winston)
const morganFormat = ':method :url :status :response-time ms';
app.use(
  morgan(morganFormat, {
    stream: {
      write: (message) => {
        const logObject = {
          method: message.split(' ')[0],
          url: message.split(' ')[1],
          status: message.split(' ')[2],
          responseTime: message.split(' ')[3],
        };
        Logger.http(JSON.stringify(logObject));
      },
    },
  })
);

// 5. Health Check (Kubernetes needs this!)
app.get('/health', (req, res) => {
  res.status(200).json({ status: 'UP', timestamp: new Date() });
});

// START
app.listen(PORT, () => {
  Logger.info(`Server running on http://localhost:${PORT}`);
  Logger.info(`Security & Logging modules active`);
});