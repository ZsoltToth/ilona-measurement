#!/usr/bin/env bash
ROOT_PASS=secret
docker run --name mysql --rm -e MYSQL_ROOT_PASSWORD=$ROOT_PASS -p 3306:3306  mysql:5.7
