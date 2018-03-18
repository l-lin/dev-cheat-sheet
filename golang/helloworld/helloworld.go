package main

import "fmt"
import "github.com/sirupsen/logrus"

func main() {
	logger := logrus.New()
	logger.Info("Foobar")
	logger.WithFields(logrus.Fields{
		"foo": "bar",
	}).Info("Foobar")
	fmt.Printf("Hello world!")
}
