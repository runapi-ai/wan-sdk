# Wan Video API JavaScript SDK for RunAPI

The wan video api JavaScript SDK is the language-specific package for Wan on RunAPI. Use this wan video api package for text-to-video, image-to-video, video-to-video, animation, and edit flows when your application needs JSON request bodies, task status lookup, and consistent RunAPI errors in JavaScript.

This wan video api README is the JavaScript package guide inside the public `wan-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/wan; for API reference, use https://runapi.ai/docs#wan; for SDK docs, use https://runapi.ai/docs#sdk-wan.

## Install

```bash
npm install @runapi.ai/wan
```

## Quick start

```typescript
import { WanClient } from '@runapi.ai/wan';

const client = new WanClient();
const task = await client.textToVideo.create({
  // Pass the Wan JSON request body from https://runapi.ai/docs#wan.
});
const status = await client.textToVideo.get(task.id);
```

Use `create` when you want to submit a task and return quickly, `get` when you need the latest task state, and `run` when a script should create and poll until completion. In web request handlers, prefer `create` plus webhook or later `get` polling so a worker is not held open.

## Language notes

Use the TypeScript types in `src/types.ts` and the resource classes under `src/resources` when building video applications. The available resources include text to videos, image to videos, video to videos, speech to videos, animations, images, reference to videos, and video edits. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys or callback secrets.

## Links

- Model page: https://runapi.ai/models/wan
- SDK docs: https://runapi.ai/docs#sdk-wan
- Product docs: https://runapi.ai/docs#wan
- Pricing and rate limits: https://runapi.ai/models/wan/2.2-a14b-text-to-video-turbo
- Provider comparison: https://runapi.ai/providers/alibaba
- Full catalog: https://runapi.ai/models
- Repository: https://github.com/runapi-ai/wan-sdk

## License

Licensed under the Apache License, Version 2.0.
