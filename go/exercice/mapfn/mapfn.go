package main

import "fmt"

func MapFn(f func(int) int, arr []int) []int {
	r := make([]int, len(arr))
	for i, v := range arr {
		r[i] = f(v)
	}
	return r
}

func main() {
	result := MapFn(func(a int) int {
		return a * 2
	}, []int{1, 2})

	fmt.Printf("%v\n", result)
}
