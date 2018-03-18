package main

import (
	"fmt"
	"time"
	"log"
)

func main() {
	defer timeTrack(time.Now(), "concurrency")

	a := []int{7, 2, 8, -9, 4, 0}
	r1 := sum(a[:len(a)/2])
	r2 := sum(a[len(a)/2:])
	x, y := <- r1, <- r2

	fmt.Println(x, y)
}

func sum(a []int) chan int{
	c := make(chan int)
	go func() {
		sum := 0
		for _, v := range a {
			sum += v
		}
		c <- sum
	}()
	return c
}

func timeTrack(start time.Time, name string) {
	elapsed := time.Since(start)
	log.Printf("%s took %dms", name, elapsed.Nanoseconds()/10e5)
}
