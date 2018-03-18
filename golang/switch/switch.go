package main

func unhex(c byte) byte {
	switch {
	case '0' <= c && c <= '9':
		println("First case")
		return c - '0'
	case 'a' <= c && c <= 'f':
		println("Second case")
		return c - 'a' + 10
	case 'A' <= c && c <= 'F':
		println("Third case")
		return c - 'A' + 10
	}
	println("Default case")
	return 0
}

func switchFallthrough(a int) {
	switch {
	case a == 0:
		fallthrough
	case a == 1:
		println("In case 1")
	case a == 2:
		println("In case 2")
	default:
		println("In default case")
	}
}

func shouldEscape(c byte) bool {
	switch c {
	case ' ', '?', '&', '=', '#', '+': // as "or"
		return true
	}
	return false
}

func main() {
	var a byte = '2'
	println(unhex(a))

	var b byte = 'a'
	println(unhex(b))

	c := 'F'
	println(unhex((byte)(c)))

	var d byte = 2
	println(unhex(d))

	println("Fallthrough")
	switchFallthrough(0)
	switchFallthrough(1)
	switchFallthrough(3)

	println("Case using comma")
	println(shouldEscape((byte)(' ')))
	println(shouldEscape((byte)('+')))
	println(shouldEscape((byte)('A')))
}
