package stack

import "strconv"

type Stack struct {
	i   int
	arr [10]int
}

func (s *Stack) push(a int) {
	println(s.i)
	s.arr[s.i] = a
	s.i++
}

func (s *Stack) pop() int {
	s.i--
	return s.arr[s.i]
}

func (s *Stack) String() (str string) {
	for i := 0; i < s.i; i++ {
		str += "[" + strconv.Itoa(i) + "," + strconv.Itoa(s.arr[i]) + "]"
	}
	return
}
