/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg04 - Publish Subscribe Simulation
 * Student(s) Name(s):  Matt Hurt
 */

package main

import (
	"fmt"
	"sync"
	"time"
)

// The key data structure here is topics, which maps topic names into a slice of channels.
// Each channel represents a subscription to the topic.  The struct fields aren't
// exported.  Clients interact with PubSub solely using its methods.
//type PubSub struct {
//	mutex sync.Mutex
//	//topics map[string][]chan string
//	subs map[string][]chan string
//	person string
//	//  Closing the channel to signal that some job is done and resources can be cleaned up.
//	closed bool
//}

//// Constructor
//func NewPubTopic() *PubSub{
//	ps := &PubSub{}
//	ps.subs = make(map[string][]chan string)
//	return ps
//}

// TODO: creates and returns a new channel on a given topic, updating the PubSub struct
// To subscribe, the client will provide: 1. The topic it's interested in  2. A channel on
// which Pubsub will send it new messages for this topic from now on.
/*
func (ps PubSub) subscribe(topic string) chan string {       |
	return nil                                               |   <-- Original given function.
}                                                            |
*/
func (ps *PubSub) subscribe(topic string, ch chan string) {
	ps.mutex.Lock()
	defer ps.mutex.Unlock()

	ps.subs[topic] = append(ps.subs[topic], ch)

}

// TODO: writes the given message on all the channels associated with the given topic.
/*
* func (ps PubSub) publish(topic string, msg string) {         |<-- Original given function.
*
* If ps.subs has no topic key, it returns a default value for its value type, or an empty slice
* of chan string.  This can be appended to and the result is what we expect regardless of the
* initial contents of ps.subs.
*
* Publishing on the Pubsub is done with the Publish method, which takes a topic and the message:
 */
//func (ps *Pubsub) Publish(topic string, msg string){
//    ps.mu.Lock()
//    defer ps.mu.Lock()
//    // Adding a closed flag to the Pubsub struct.  It's initialized to false in the constructor.
//    if ps.closed{
//    return
//    }
//    // **Perform each send in its own goroutine.**
//    // If ch is unbuffered, then ch <- msg will block until the message is consumed by a receiver.
//    for _, _ = range ps.subs[topic] {
//        // This will prevent ch <- msg from blocking ALL clients in Pubsub when other subscriber's are
//        // notified on the same channel.
//        go func(ch chan string) {
//			ch <- msg
//			// Now it doesn't matter how much buffering each channel has; the send will not block any
//			// sends because it runs in its own goroutine.  Which might be a performance issue by
//			// starting new one to run and tearing it down for every message.  When in doubt benchmark it.
//
//		}
//        (ch)
//    }
//}

/* Close should be called to signal on all the subscription channels that no more data will be sent.
*  Note-to-self: these channels weren't created by Pubsub; they are provided in calls to Subscribe.
*  If the sending side closed the channel, it would be idiomatic.  Since sending on a closed channel
*  panics, it's dangerous to close channels on the receiving side because then the sending side doesn't
*  know that the channel it is sending into may be closed.
 */
//func (ps *PubSub) Close() {
//    ps.mu.Lock()
//    defer ps.mu.Unlock()
//
//    if !ps.closed {
//        ps.closed = true
//        for _, subs := range ps.subs {
//            for _, ch := range subs {
//            close(ch)
//            }
//        }
//    }
//}

// TODO: sends messages taken from a given array of message, one at a time and
// at random intervals, to all topic subscribers.
func publisher(ps PubSub, topic string, msgs []string) {

}

/*
*  Create the subscription channels in Pubsub.  Only the Subscribe method would
*  have to change to create subscription channels in Pubsub.  The buffer size is
*  hardcoded to 1.
*
*  If the desired behavior is to let the client configure the buffer size
*  with an argument then this can be done either in the constructor for
*  all subscriptions, or in the Subscribe with a different buffer size per subscription.
*
*  This function creates and closes the channels so the separation of responsibilites
*  is cleaner.
*
*  Issue: The inconvenience with this approach is that clients may want to subscribe to
*  the same channel to multiple topics.
*
*  Reference: https://eli.thegreenplace.net/2020/pubsub-using-channels-in-go/
 */
//func (ps *PubSub) subscribe(topic string) <-chan string{
//  ps.mu.Lock()
//  defer ps.mu.Unlock()
//
//  ch := make(chan string, 1)
//  ps.subs[topic] = append(ps.subs[topic], ch)
//  return ch
//}

// TODO: reads and displays all messages received from a particular topic
/*
*  Subscribers just get a channel and listen on it until it's closed.
 */
func subscriber(ps PubSub, name string, topic string) {
}

func (ps PubSub) sendMessage(message chan string) *sync.WaitGroup {
	ps.mutex.Lock()
	fmt.Println("Starting go routine")
	time.Sleep(1 * time.Second)
	message <- "You just sent a message... go routine terminated."
	ps.mutex.Unlock()
	wg.Done()
}

func (ps PubSub) main() {
	fmt.Print("Program executed...")
	// TODO: create the ps struct
	//person := "Mary"
	//topic := make(chan string)
	//defer close(topic)
	// TODO: create the arrays of messages to be sent on each topic
	messages := make(chan string)
	defer close(messages)
	wg.Add(2)
	go ps.sendMessage(messages, &wg)
	wg.Wait()

	message := <-messages
	fmt.Println(message)

	// TODO: set wait group to 2 (# of publishers)
	time.Sleep(1 * time.Second)
	// TODO: create the publisher goroutines
	//go publisher(messages, topic,msgstring)

	// TODO: create the subscriber goroutines
	//go subscriber()
	//fmt.Println("")

	// TODO: wait for all publishers to be done
	//fmt.Println(publishers())
}
