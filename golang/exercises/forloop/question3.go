package main

import "fmt"

func main() {
	var arr [100]int
	for i := 0; i < 100; i++ {
		arr[i] = i
	}
	fmt.Printf("%v\n", arr)
}
