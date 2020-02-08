#!/bin/bash

HISTFILE=~/.bash_history
set -o history
history | awk '{a[$2]++}END{for(i in a){print a[i] " " i}}' | sort -rn | head

