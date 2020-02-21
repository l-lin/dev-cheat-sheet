package fibonacci

import (
	"testing"
	"fmt"
)

func TestFibonacci(t *testing.T) {
	a := Fibonacci(3)
	if a != 2 {
		t.Fail()
	}
	if Fibonacci(4) != 3 {
		t.Fail()
	}
	if Fibonacci(5) != 5 {
		t.Fail()
	}
}

func TestFibonacciArr(t *testing.T) {
	arr := FibonacciArr(50)
	fmt.Printf("%v\n", arr)
}
