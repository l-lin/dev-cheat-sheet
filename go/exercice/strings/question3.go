package main

import (
	"unicode/utf8"
	"fmt"
)

func main() {
	a := "asSASA ddd dsjkdsjs dk"
	a = a[0:4] + "abc" + a[5:]
	fmt.Printf("%s\n", a)
	fmt.Printf("String %s\nLength: %d, Runes: %d\n", a, len([]byte(a)), utf8.RuneCount([]byte(a)))
}
