import type { Request, Response, NextFunction } from 'express';
import { ZodError } from 'zod';
import type { ZodTypeAny } from 'zod';
import { Logger } from '../utils/logger.js';

export const validate = (schema: ZodTypeAny) => (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  try {
    schema.parse({
      body: req.body,
      query: req.query,
      params: req.params,
    });
    next();
  } catch (e: any) {
    if (e instanceof ZodError) {
      Logger.warn(`Validation Error: ${e.message}`);
      return res.status(400).send(e.issues);
    }
    next(e);
  }
};