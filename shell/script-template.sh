#!/usr/bin/env bash

set -e

show_help() {
cat << EOF
Usage: ${0##*/} <flags> <args>
    -h, --help             Display help
    -g, --greet            Greet the world
EOF
}

greet_user() {
  echo "Hello, world"
}

main() {
    local greet=
    while :; do
        case "${1:-}" in
            -h|--help)
                show_help
                exit
                ;;
            -g|--greet)
                greet=true
                ;;
            -?*)
                printf 'WARN: Unknown option (ignored): %s\n' "$1" >&2
                ;;
            *)
                break
                ;;
        esac

        shift
    done

    if [[ -n "$greet" ]]; then
        greet_user
    fi
}

main "$@"
