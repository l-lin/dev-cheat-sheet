#!/usr/bin/env bash
# -----------------------------------------
# script description here
# -----------------------------------------

set -e

foo=${FOO:-foo}

show_help() {
  cat << EOF
Script description here

Usage: ${0##*/} <flags> <args>

Examples:
    # Prompt name when greeting
      ${0##*/} greet
    # Greet Louis without prompting
      ${0##*/} greet --name Louis
    # Use foo
      FOO=foobar ${0##*/} foo
    # Use bar
      ${0##*/} bar azerty

Available commands:
    greet                 Greet someone
    foo                   Display 'foo'
    bar                   Display 'bar'

Flags:
    -h, --help            Display help
    -n, --name            Name of the person to greet
    -v, --verbose         Verbose

Environment variables:
    FOO                   Some environment variable to set

EOF
}

greet_user() {
  local name="${1}"
  if [[ -z "${name}" ]]; then
    read -p "person to greet: " -r name
  fi
  echo "Hello, ${name}"
}

main() {
  local name=
  local verbose=false
  # Flags in bash tutorial here: /usr/share/doc/util-linux/examples/getopt-parse.bash
  TEMP=$(getopt -o 'hn:v' --long 'help,name:,v' -n 'script-template.sh' -- "$@")
  eval set -- "$TEMP"
  unset TEMP
  while true; do
    case "${1}" in
      '-h'|'--help')
        show_help
        exit
        ;;
      '-n'|'--name')
        name="${2}"
        shift 2
        continue
        ;;
      '-v'|'--verbose')
        verbose=true
        echo "Verbose mode: ${verbose}"
        shift
        continue
        ;;
      '--')
        shift
        break
        ;;
      *)
        break
        ;;
    esac

    shift
  done

  case "${1}" in
    'greet')
      greet_user "${name}"
      ;;
    'foo')
      echo "foo ${foo}"
      ;;
    'bar')
      echo "bar ${2}"
      ;;
    *)
      show_help
      ;;
  esac
}

main "$@"
