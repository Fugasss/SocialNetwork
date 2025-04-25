FROM node:23-slim AS builder

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

FROM node:23-slim

WORKDIR /app

COPY --from=builder /app/.next/standalone ./

COPY --from=builder /app/public ./public
COPY --from=builder /app/.next/static ./.next/static

ENV NODE_ENV=production

EXPOSE 3000

CMD ["sh", "-c", "HOST=0.0.0.0 node server.js"]