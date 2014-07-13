package avg

import "testing"

func TestAvg(t *testing.T) {
	a := []float64{1, 1, 1}
	if Avg(a) != 1.0 {
		t.Log("Avg must be equal to 1")
		t.FailNow()
	}

	b := []float64{0, 100}
	if Avg(b) != 50.0 {
		t.FailNow()
	}
}
