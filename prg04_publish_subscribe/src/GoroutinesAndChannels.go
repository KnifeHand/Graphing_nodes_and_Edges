package main

import (
	"fmt"
	"sync"
	"time"
)

func producer(wg *sync.WaitGroup, taskChannel chan<- int,
	nTasks int) {
	defer wg.Done()
	for task := 1; task <= nTasks; task++ {
		taskChannel <- task
		fmt.Println("Producer produced task #", task)
	}
	fmt.Println("Producer is done!!")
}
func consumer(wg *sync.WaitGroup, taskChannel <-chan int,
	nTasks int) {
	defer wg.Done()
	for total := 1; total <= nTasks; total++ {
		task := <-taskChannel
		fmt.Println("Consumer consmed task #", task)
		time.Sleep(3 * time.Second)
	}
}

func main() {
	taskChannel := make(chan int, 5)

	var waitGroup sync.WaitGroup
	waitGroup.Add(2) // wait for 2 goroutinges to call Done()

	go producer(&waitGroup, taskChannel, 20)
	go consumer(&waitGroup, taskChannel, 20)

	waitGroup.Wait()
}
