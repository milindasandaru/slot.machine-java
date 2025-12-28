// Entry point for the server
import express from 'express';
import router from './routes/routes.ts';

const app = express();
const port = process.env.PORT || 3000;

app.get('/', (req, res) => res.send('Hello from ScopeMaster!'));
app.use(express.json());
app.use('/api', router);

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});

export default app;
