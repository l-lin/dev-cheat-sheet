#!/usr/bin/env bash

awslocal s3 mb s3://my-bucket

awslocal sqs create-queue --queue-name my-sqs-queue

awslocal dynamodb create-table \
    --table-name my-dynamodb-table \
    --key-schema AttributeName=id,KeyType=HASH \
    --attribute-definitions AttributeName=id,AttributeType=S \
    --billing-mode PAY_PER_REQUEST

