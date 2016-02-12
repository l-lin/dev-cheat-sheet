#!/bin/bash

names=(
    'Foo'
    'Bar'
    'Foobar'
)

count=0
while [ "x${names[count]}" != "x" ]
do
    echo "${names[count]}"
    count=$(( $count + 1 ))
done

exit 0
