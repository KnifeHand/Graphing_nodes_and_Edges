/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg04 - Publish Subscribe Simulation
 * Student(s) Name(s):  Larsen Close and Matt Hurt
 */

package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

// PubSub is our struct for the publisher-subscriber pattern
type PubSub struct {
	mu     sync.Mutex
	topics map[string][]chan string
}

var wg sync.WaitGroup

// TODO: writes the given message on all the channels associated with the given topic
func (ps PubSub) publish(topic string, msg string) {
	ps.mu.Lock()
	//defer ps.mu.Unlock()
	for _, ch := range ps.topics[topic] {
		//ps.topics[topic] = append(ps.topics[topic], ch)
		go func(chl chan string) {
			chl <- msg
		}(ch)
	}
	ps.mu.Unlock()
}

// TODO: sends messages taken from a given array of message, one at a time and at random intervals, to all topic subscribers
func publisher(ps PubSub, topic string, msgs []string) {
	//ps.mu.Lock()
	//defer ps.mu.Unlock()
	time.Sleep(3 * time.Second)
	for _, message := range msgs {
		//ps.publish(topic, msgs[message])
		time.Sleep(time.Duration(rand.Intn(5)) * time.Second)
		ps.publish(topic, message)
	}
	time.Sleep(3 * time.Second)
	wg.Done()
}

// TODO: creates and returns a new channel on a given topic, updating the PubSub struct
func (ps PubSub) subscribe(topic string) <-chan string {
	ps.mu.Lock()
	ch := make(chan string)
	ps.topics[topic] = append(ps.topics[topic], ch)
	ps.mu.Unlock()
	return ch
}

// TODO: reads and displays all messages received from a particular topic
func subscriber(ps PubSub, name string, topic string) {

	ch := ps.subscribe(topic)
	for {
		if fact, ready := <-ch; ready {
			fmt.Println(name + "message received: " + fact)
		} else {
			break
		}
	}
}

//func printer(topic string, channel []chan string,) {
//	for item := range channel {
//		var receive = <-channel[item]
//		fmt.Printf("Topic: %s; Message: %s\n", topic, receive)
//	}
//}

func main() {

	// TODO: create the ps struct
	ps := PubSub{topics: make(map[string][]chan string)}

	// TODO: create the arrays of messages to be sent on each topic
	//var beesArray [5]string
	beesArray := []string{
		//"1","2","3","4","5",
		//}
		//{
		"bees are pollinators",
		"bees are pollinators",
		"all worker bees are female",
		"bees have 5 eyes",
		"bees fly about 20mph",
	}

	//beesArray[0] = "bees are pollinators"
	//beesArray[1] = "bees produce honey"
	//beesArray[2] = "all worker bees are female"
	//beesArray[3] = "bees have 5 eyes"
	//beesArray[4] = "bees fly about 20mph"

	//var philosophyArray [5]string
	philosophyArray := []string{
		//"6","7","8","9","10",
		//}
		//{
		"And if you gaze for long into an abyss, the abyss gazes also into you.",
		"Dreams are the touchstones of our characters.",
		"The question is not what you look at, but what you see.",
		"Go confidently in the direction of your dreams! Live the life you’ve imagined.",
		"Rather than love, than money, than fame, give me truth.",
	}

	//philosophyArray[0] = "And if you gaze for long into an abyss, the abyss gazes also into you."
	//philosophyArray[1] = "Dreams are the touchstones of our characters."
	//philosophyArray[2] = "The question is not what you look at, but what you see."
	//philosophyArray[3] = "Go confidently in the direction of your dreams! Live the life you’ve imagined."
	//philosophyArray[4] = "Rather than love, than money, than fame, give me truth."

	// TODO: set wait group to 2 (# of publishers)
	wg.Add(2)

	// TODO: create the publisher goroutines

	go publisher(ps, "bee facts", beesArray)
	go publisher(ps, "philosophy quotes", philosophyArray)

	// TODO: create the subscriber goroutines
	go subscriber(ps, "Maria ", "bee facts")
	go subscriber(ps, "Thomas ", "philosophy quotes")
	go subscriber(ps, "Jim ", "philosophy quotes")

	// TODO: wait for all publishers to be done

	wg.Wait()

}
