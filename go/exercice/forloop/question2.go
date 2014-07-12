package main

import "fmt"

func main() {
	i := 0
Loop:
	fmt.Printf("%d\n", i)
	i++
	if i < 100 {
		goto Loop
	}
}
