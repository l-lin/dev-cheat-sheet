package main

import "fmt"

func main() {
	s := "Hello world"
	c := []rune(s) // rune == int32
	c[0] = 'c'
	s = string(c)
	fmt.Printf("%s\n", s) // prints "cello world"

	s = "Hello world"
	fmt.Printf("%s\n", s) // prints "Hello world"

	s = "c" + s[1:]
	fmt.Printf("%s\n", s) // prints "cello world"
}
