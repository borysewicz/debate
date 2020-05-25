FROM node:current-alpine

RUN npm install --global http-server

WORKDIR /static

EXPOSE 80

COPY /dist/Debate-frontend .

CMD http-server -p 80 .