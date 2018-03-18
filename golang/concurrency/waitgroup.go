package main

import (
	"sync"
	"log"
)

func main() {
	var done sync.WaitGroup
	for i := 0; i < 4; i++ {
		done.Add(1)
		go func(i int, done *sync.WaitGroup) {
			log.Printf("[-] Doing some stuffs in this %d loop", i)
			done.Done()
		}(i, &done)
	}
	done.Wait()
}
