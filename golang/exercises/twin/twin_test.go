package main

import "testing"

func TestIsTwin(t *testing.T) {
	type given struct {
		a string
		b string
	}
	var tests = map[string]struct {
		given    given
		expected bool
	}{
		"is twin": {
			given:    given{a: "Romain", b: "Marion"},
			expected: true,
		},
		"not same length": {
			given:    given{a: "foobar", b: ""},
			expected: false,
		},
		"empty strings": {
			given:    given{a: "", b: ""},
			expected: true,
		},
		"same length at the start but not when computed": {
			given:    given{a: "foobar", b: "moliku"},
			expected: false,
		},
		"same length & same words but not same number": {
			given:    given{a: "foobar", b: "fobaar"},
			expected: false,
		},
		"same word": {
			given:    given{a: "foobar", b: "foobar"},
			expected: true,
		},
	}
	for name, tt := range tests {
		t.Run(name, func(t *testing.T) {
			actual := isTwin(tt.given.a, tt.given.b)
			if actual != tt.expected {
				t.Errorf("expected %v, actual %v", tt.expected, actual)
			}
		})
	}
}
