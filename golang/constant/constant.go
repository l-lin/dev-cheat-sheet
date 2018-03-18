package main

import "fmt"

const (
	A = iota 	// Starts at 0
	B = iota 	// == 1
	C 			// iota implicit => 2
	D 			// == 3
)

func main() {
	fmt.Println(A)
	fmt.Println(B)
	fmt.Println(C)
	fmt.Println(D)
}
