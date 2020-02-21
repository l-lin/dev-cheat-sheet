package plustwo

func PlusTwo() func(int) int {
	return func(a int) int {
		return a + 2
	}
}
