package minmax

func Min(arr []int) (min int) {
	min = arr[0]
	for _, v := range arr {
		if v < min {
			min = v
		}
	}
	return
}

func Max(arr []int) (max int) {
	max = arr[0]
	for _, v := range arr {
		if v < max {
			max = v
		}
	}
	return
}
