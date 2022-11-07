# stage1 as builder
FROM node:lts-alpine as builder
WORKDIR /app
# Copy the package.json and install dependencies
COPY package*.json ./
#RUN npm install
RUN npm install --legacy-peer-deps
# Copy rest of the files
COPY . .
# Build the project
RUN npm run build
FROM nginx:alpine as production-build
# for debugging purpose
RUN apk add curl
#RUN apk add dos2unix
# Copy entrypoint script as /entrypoint.sh
COPY ./entrypoint.sh /entrypoint.sh
# make the 'app' folder the current working directory
WORKDIR /app
# Copy from the stahg 1
COPY --from=builder /app/dist /usr/share/nginx/html
COPY ./.nginx/nginx.conf /etc/nginx/nginx.conf
#ENTRYPOINT ["nginx", "-g", "daemon off;"]
RUN ls -l
# Grant Linux permissions and run entrypoint script
RUN chmod +x /entrypoint.sh
#CMD [ "/entrypoint.sh" ]
#ENTRYPOINT ["nginx", "-g", "daemon off;"]
#RUN dos2unix /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]
