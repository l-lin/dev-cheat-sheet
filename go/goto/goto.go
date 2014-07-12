package main

func main() {
	println("Before Gotos...")
	goto Goto1
	println("Before Goto1") // Never executed
Goto1:
	println("Goto1")
	goto Goto2
	println("Before Goto2") // Never executed
Goto2:
	println("Goto2")
}
