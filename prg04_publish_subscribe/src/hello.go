package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	//fmt.Printf("hello, world\n")

	//const FILE_NAME  = "data.txt"
	//fmt.Printf("File name: %s\n", FILE_NAME)

	var name string
	reader := bufio.NewReader(os.Stdin)
	fmt.Printf("Name?: ")
	name, _ = reader.ReadString('\n')
	fmt.Printf("Name: %s", name)

	var age int64
	fmt.Printf("Age: ")
	fmt.Scanf("%d", &age)
	fmt.Printf("Age: %d", age)
}
