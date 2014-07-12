package main

import "fmt"

func changeFirstValue(arr [5]int) {
	// Parameter is a copy of the array, not the pointer!
	arr[0] = 32
}

func printArray(arr []int) {
	for _, value := range arr {
		print(value)
		print(" - ")
	}
	println()
}

func main() {
	var arr [5]int
	arr[0] = 42
	arr[1] = 13
	fmt.Printf("The first element is %d\n", arr[0])

	changeFirstValue(([5]int)(arr))
	fmt.Printf("The first element is still %d\n", arr[0])

	matrix := [2][2]int{{1, 2}, {3, 4}}
	fmt.Printf("The cell [0, 0] is %d\n", matrix[0][0])

	println("Slice")
	// Create slice with 10 elements capacity
	slice := make([]int, 10)
	fmt.Printf("Length of the slice is %d\n", len(slice))
	fmt.Printf("Capacity of the slice is %d\n", cap(slice))
	arr = [5]int{1, 2, 3, 4, 5}
	slice = arr[0:3]
	fmt.Printf("Length of the slice is %d\n", len(slice))
	fmt.Printf("Capacity of the slice is %d\n", cap(slice))

	a := [...]int {1, 2, 3, 4, 5}
	s1 := a[2:4]
	s2 := a[1:5]
	s3 := a[:]
	s4 := a[:4]
	s5 := s2[:]
	printArray(([]int)(s1))
	printArray(([]int)(s2))
	printArray(([]int)(s3))
	printArray(([]int)(s4))
	printArray(([]int)(s5))

	println("Append")
	s6 := append(s5, 100)
	printArray(([]int)(s6))

	s7 := []int {0, 0}
	s8 := append(s7, 2)
	s9 := append(s8, 3, 5, 7)
	s10 := append(s9, s7...) // Note the three dots
	printArray(([]int)(s7))
	printArray(([]int)(s8))
	printArray(([]int)(s9))
	printArray(([]int)(s10))

	println("Copy")
	var b = [...]int {0, 1, 2, 3, 4, 5, 6, 7}
	var s = make([]int, 6)
	n1 := copy(s, b[0:])
	fmt.Printf("Number of elements copied is %d\n", n1)
	printArray(([]int)(s))
	n2 := copy(s, s[2:])
	fmt.Printf("Number of elements copied is %d\n", n2)
	printArray(([]int)(s))
}
