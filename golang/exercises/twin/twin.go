package main

import (
	"fmt"
	"strings"
)

func main() {
	fmt.Println(`isTwin("Marion", "Romain")`, isTwin("Marion", "Romain"))
}

func isTwin(a, b string) bool {
	if len(a) != len(b) {
		return false
	}
	mA := map[string]int{}
	mB := map[string]int{}
	for i := 0; i < len(a); i++ {
		mA[strings.ToLower(string(a[i]))]++
		mB[strings.ToLower(string(b[i]))]++
	}
	if len(mA) != len(mB) {
		return false
	}
	for k, v := range mA {
		if mB[k] != v {
			return false
		}
	}
	return true
}
