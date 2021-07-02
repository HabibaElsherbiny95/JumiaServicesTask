# JumiaServicesTask

**To build & run a docker image for the backend:** 
1. Go to directory backend-task 
2. Run the following commands: 
   - docker build -t jumia-backend-task .
   - docker run -p 8088:8088 jumia-backend-task

**To build & run a docker image for the frontend:**
1. Go to directory frontend-task
2. Run the following commands:
    - docker build -t jumia-frontend-task .
    - docker run -it --rm -v ${PWD}:/app -v /app/node_modules -p 3001:3000 -e CHOKIDAR_USEPOLLING=true jumia-frontend-task

**To try the app:**
1. Open the browser
2. Go to http://localhost:3001
3. Filter the table entries by country and/or state 
