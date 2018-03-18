package bubblesort

import (
	"testing"
	"fmt"
)

func TestBubbleSort(t *testing.T) {
	arr := []int{2, 3, 1}
	BubbleSort(arr)
	fmt.Printf("%v\n", arr)
}
