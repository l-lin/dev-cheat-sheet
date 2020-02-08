#!/bin/bash

if [ -z "$1" ]
then
    echo "Please set the text to search"
    echo "Usage: $0 <text_to_search>"
    exit -1
fi

grep -Ri "$1" *
