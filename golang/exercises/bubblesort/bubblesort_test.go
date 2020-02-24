package main

import (
	"testing"
)

func TestBubbleSort(t *testing.T) {
	var tests = map[string]struct {
		given    []int
		expected []int
	}{
		"sort": {
			given:    []int{3, 1, 2, 5, 8, 4},
			expected: []int{1, 2, 3, 4, 5, 8},
		},
	}
	for name, tt := range tests {
		t.Run(name, func(t *testing.T) {
			actual := bubbleSort(tt.given)
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
