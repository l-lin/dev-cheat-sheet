package integerordering

func Order(a, b int) (int, int) {
	if a > b {
		return b, a
	}
	return a, b
}
