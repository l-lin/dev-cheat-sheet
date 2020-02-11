#!/usr/bin/env bash
# -----------------------------------------
# script description here
# -----------------------------------------

set -e

show_help() {
  cat << EOF
Usage: ${0##*/} <flags> <args>
    -h, --help             Display help
    -g, --greet            Greet the world
    -v, --verbose          Verbose
EOF
}

greet_user() {
  local name="${1-World}"
  echo "Hello, ${name}"
}

main() {
  local greet=
  local verbose=false
  # Flags in bash tutorial here: /usr/share/doc/util-linux/examples/getopt-parse.bash
  TEMP=$(getopt -o 'hg:v' --long 'help,greet:,v' -n 'script-template.sh' -- "$@")
  eval set -- "$TEMP"
  unset TEMP
  while true; do
    case "${1}" in
      '-h'|'--help')
        show_help
        exit
        ;;
      '-g'|'--greet')
        greet="${2}"
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

  if [[ -n "${greet}" ]]; then
    greet_user "${greet}"
  fi

  for arg; do
    echo "--> '$arg'"
  done
}

main "$@"
