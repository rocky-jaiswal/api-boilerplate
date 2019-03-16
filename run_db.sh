#!/bin/sh

docker run -it --name api-dev-db -e POSTGRES_USER=api_dev_db -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:11-alpine
