#!/usr/bin/env bash

mysql -h 127.0.0.1 -u root  -psecret < ./sql/create-user.sql
mysql -h 127.0.0.1 -u ilona -psecret < ./sql/dataset.sql
