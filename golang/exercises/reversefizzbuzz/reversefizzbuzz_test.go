package main

import "testing"

func TestReverseFizzBuzz(t *testing.T) {
	var tests = map[string]struct {
		given    string
		expected []int
	}{
		"reverse": {
			given:    "1 2 Fizz 4 Buzz Fizz 7 8 Fizz Buzz 11 Fizz 13 14 FizzBuzz 16 17 Fizz 19 Buzz Fizz 22 23 Fizz Buzz 26 Fizz 28 29 FizzBuzz",
			expected: []int{3, 5},
		},
		"empty string": {
			given:    "",
			expected: []int{},
		},
		"no fizz buzz": {
			given:    "1 2 3 4 5",
			expected: []int{},
		},
	}
	for name, tt := range tests {
		t.Run(name, func(t *testing.T) {
			actual := reverseFizzBuzz(tt.given)
			if len(actual) != len(tt.expected) {
				t.Errorf("expected length %d, actual length %d", len(tt.expected), len(actual))
			} else {
				for i := 0; i < len(actual); i++ {
					if actual[i] != tt.expected[i] {
						t.Errorf("%d: expected %d, actual %d", i, tt.expected[i], actual[i])
					}
				}
			}
		})
	}
}
