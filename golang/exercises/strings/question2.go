package main

import (
	"unicode/utf8"
	"fmt"
)

const (
	CONST = "asSASA ddd dsjkdsjs dk"
)

func main() {
	fmt.Printf("String %s\nLength: %d, Runes: %d\n", CONST, len([]byte(CONST)), utf8.RuneCount([]byte(CONST)))
}
