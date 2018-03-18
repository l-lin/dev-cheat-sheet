package fibonacci

func Fibonacci(a int) int {
	if a == 1 || a == 2 {
		return 1
	}
	return Fibonacci(a - 1) + Fibonacci(a - 2)
}

func FibonacciArr(a int) []int {
	b := make([]int, a)
	b[0], b[1] = 1, 1

	for i := 2; i < a; i++ {
		b[i] = b[i - 1] + b[i - 2]
	}
	return b
}
