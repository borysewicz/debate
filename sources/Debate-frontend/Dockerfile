FROM node

RUN npm install --global http-server

WORKDIR /static

EXPOSE 9000

COPY /dist/Debate-frontend .

CMD http-server -p 9000 .