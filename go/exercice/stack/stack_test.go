package stack

import (
	"testing"
	"fmt"
)

func TestPushPop(t *testing.T) {
	s := new(Stack)
	s.push(1)
	fmt.Printf("Stack representation: %v\n", s)
	a := s.pop()
	if a != 1 {
		t.Log("a should be equal to 1")
		t.Fail()
	}
}
