package variables

import (
	"fmt"
)

func main() {
	// Manipulating int
	var a, b int = 1, 2
	c := a + b;
	fmt.Println(c)

	// string
	var hello string = "Hello"
	var world string = "World"
	fmt.Println(hello + world)

	// _ => discarded
	var _, d = 34, 35
	fmt.Println(d)
}
