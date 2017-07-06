#!/bin/sh

cd frontend
npm run-script build
cd ..
cp frontend/dist/*.js src/main/resources/consoleroot/js/