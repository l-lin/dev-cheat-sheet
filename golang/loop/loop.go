package main

import "fmt"

func main() {
	sum := 0
	for i := 0; i < 3; i++ {
		sum += i
	}
	println(sum)

	// Reverse array
	println("Reverse array")
	a := [5]int{2, 4, 5, 9, 1}
	for i, j := 0, len(a) - 1 ; i < j ; i, j = i+1, j-1 {
		a[i], a[j] = a[j], a[i]
	}

	for i := 0; i < len(a); i++ {
		println(a[i])
	}

	// Break / continue
	println("Break / continue")
	sum = 0
	for i := 0; i < len(a); i++ {
		if i > 2 {
			break;
		}
		if i == 0 {
			continue;
		}
		sum += a[i]
	}
	println(sum)

	// Range
	println("Range")
	for key, value := range a {
		fmt.Printf("Key = %d - Value = %d\n", key, value)
	}
	// If no use of index
	for _, value := range a {
		fmt.Printf("Value = %d\n", value)
	}
}
