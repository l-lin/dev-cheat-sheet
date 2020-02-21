package integerordering

import "testing"

func TestOrder(t *testing.T) {
	a, b := Order(1, 2)
	if a != 1 || b != 2 {
		t.FailNow()
	}

	c, d := Order(4, 3)
	if c != 3 || d != 4 {
		t.FailNow()
	}
}
