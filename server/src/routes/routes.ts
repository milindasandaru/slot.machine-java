import { Router } from "express";
import { createAnalysis } from "../controllers/request.controller.ts";

const router = Router();

router.post("/analyze", createAnalysis);

export default router;