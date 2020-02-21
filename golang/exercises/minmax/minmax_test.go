package minmax

import "testing"

func testMin(t *testing.T) {
	if Min([]int{1, 2, 3}) != 1 {
		t.Fail()
	}
	if Min([]int{30, 10, 22}) != 10 {
		t.Fail()
	}
}

func testMax(t *testing.T) {
	if Max([]int{1, 2, 3}) != 3 {
		t.Fail()
	}
	if Max([]int{30, 10, 22}) != 30 {
		t.Fail()
	}
}
