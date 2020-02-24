package main

import (
	"strconv"
	"strings"
)

func reverseFizzBuzz(s string) []int {
	answers := []int{}
	if s == "" {
		return answers
	}
	arr := strings.Split(s, " ")
	for i := 0; i < len(arr); i++ {
		if _, err := strconv.Atoi(arr[i]); err != nil {
			if !isMultiple(i+1, answers) {
				answers = append(answers, i+1)
			}
		}
	}
	return answers
}

func isMultiple(n int, answers []int) bool {
	for _, m := range answers {
		if n%m == 0 {
			return true
		}
	}
	return false
}
