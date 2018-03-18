package main

import "fmt"

func main() {
	// Declaring and instancing
	monthdays := map[string]int {
		"Jan": 31, "Feb": 28, "Mar": 31,
		"Apr": 30, "May": 31, "Jun": 30,
		"Jul": 31, "Aug": 31, "Sep": 30,
		"Oct": 31, "Nov": 30, "Dec": 31, // Comma required
	}
	println(monthdays["Jan"])
	for k, _ := range monthdays {
		fmt.Println(k, monthdays[k])
	}

	// Just instancing
	monthdays2 := make(map[string]int)
	monthdays2["Jan"] = 31
	println(monthdays2["Jan"])
	monthdays2["Jan"] = 30
	println(monthdays2["Jan"])

	// Check if value is present
	value, present := monthdays2["Jan"]
	println(value);
	println(present)

	// Delete
	delete(monthdays2, "Jan")
	value, present = monthdays2["Jan"]
	println(value); 	// Default value == 0
	println(present) 	// false
}
