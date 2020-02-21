package plustwo

import "testing"

func TestPlusTwo(t *testing.T) {
	p := PlusTwo()
	if p(2) != 4 {
		t.Fail()
	}
}
