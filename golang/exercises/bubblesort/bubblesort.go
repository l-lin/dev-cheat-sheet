package main

import "log"

func bubbleSort(arr []int) []int {
	sorted := make([]int, len(arr))
	copy(sorted, arr)
	log.Println(sorted)
	for i := 0; i < len(sorted)-1; i++ {
		for j := i + 1; j < len(sorted); j++ {
			if sorted[i] > sorted[j] {
				sorted[i], sorted[j] = sorted[j], sorted[i]
			}
		}
	}
	return sorted
}
