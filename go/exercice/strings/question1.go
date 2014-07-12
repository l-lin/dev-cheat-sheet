package main

func main() {
	str := "A"
	for i := 0; i < 100; i++ {
		print(str)
		str += "A"
		println()
	}
}
