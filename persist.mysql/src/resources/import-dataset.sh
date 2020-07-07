#!/usr/bin/env bash

mysql -h 127.0.0.1 -u root  -psecret < ./create-user.sql
mysql -h 127.0.0.1 -u ilona -psecret < ./dataset.sql
