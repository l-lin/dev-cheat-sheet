#!/bin/sh
# http://askubuntu.com/questions/149971/how-do-you-remap-a-key-to-the-caps-lock-key-in-xubuntu#answer-149972
# -----------------------------------------
# To swap the keys go:
# Xubuntu → Settings Manager → Session and Startup
# Then in the Sessions and Startup configurator go
# Application Autostart (tab at the top) → Add (bottom button)
# Now on the Add Application screen
#   Name: Control and CapsLk swap
#   Description: Swap the two keys
#   Command: /usr/bin/setxkbmap -option "ctrl:swapcaps"
# -----------------------------------------

/usr/bin/setxkbmap -option "ctrl:swapcaps"

