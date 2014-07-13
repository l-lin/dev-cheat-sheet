package avg

func Avg(arr []float64) (average float64) {
	for _, value := range arr {
		average += value
	}
	average = average / float64(len(arr))
	return
}
