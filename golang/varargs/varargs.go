package main

import "fmt"

func varargs(a ...int) {
	for i := 0; i < len(a); i++ {
		fmt.Printf("%d\n", a[i])
	}
}

func main() {
	varargs(1, 2)
}
