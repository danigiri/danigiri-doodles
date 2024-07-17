package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rooms {


/*
 * Allocate customers to hotel rooms based on their arrival and departure days. Each customer wants
 * their own room, so two customers can stay in the same room only if the departure day of the first
 * customer is earlier than the arrival day of the second customer. The number of rooms used should
 * be minimized.
 * 
 * Input
 * 
 * A list or array of n customers, 1 ≤ n ≤ 1000. Each customer is represented by (arrival_day,
 * departure_day), which are positive integers satisfying arrival_day ≤ departure_day.
 * 
 * Output
 * 
 * A list or array of size n, where element i indicates the room that customer i was allocated.
 * Rooms are numbered 1,2, ..., k for some 1 ≤ k ≤ n. Any allocation that minimizes the number of
 * rooms k is a valid solution.
 * 
 * Example:
 * 
 * Suppose customers is [(1,5), (2,4), (6,8), (7,7)]. Clearly customers 1 and 2 cannot get the same
 * room. Customer 3 can use the same room as either of them, because they both leave before customer
 * 3 arrives. Then customer 4 can be given the other room. So any of [1,2,1,2], [1,2,2,1],
 * [2,1,2,1], [2,1,1,2] is a valid solution.
 * 
 * first solution: for every customer, allocate into a separate room
 * 
 * second solution: for every customer, allocate into room, look at the rest
 * 
 * third solution let's have a sorted structure of room times, for any given input find the latest
 * room which can allocate the input (so input.in - room.out is the smallest and >0) allocate that
 * room by incrementing if no room is found, create a new one room id is size + 1
 * 
 * need an auxiliiary structure to keep the reservations, map with lists
 * 
 * use-cases:
 * 
 * reservations: list with pairs, customer id is reservation position
 * 
 * heap tree: [time0->r2, time1->r1, time2->r3, time3->r4]
 * 
 * look for room: binary search with time, find latest room that satisfies constraints res.in -
 * room.out >0 if none found --> new room, confirm --> allocate this room, move the room in the
 * search tree, confirm
 * 
 * confirmed: array with room for each customer, by pos
 * 
 * [5,6][1,2][3,4][0,6]
 * 
 * order by size, nay order by size, and then by order by leaving departure [1,2][3,4][0,6][5,6]
 * 
 * 
 */

class Allocation implements Comparable<Allocation> {

public int	out;
public int	room;

Allocation(int out, int room) {
	this.out = out;
	this.room = room;
}

@Override
public int compareTo(Allocation a) {
	return Integer.valueOf(out).compareTo(a.out);
}
}

class Reservation implements Comparable<Reservation>{

public int	out;
public int	in;

Reservation(int in, int out) {
	this.in = in;
	this.out = out;
}

@Override
public int compareTo(Reservation r) {
	return Integer.valueOf(out).compareTo(r.out);
}

}

public static List<Integer> hotelRooms(List<Reservation> reservations) {
	// create structures first
	//var state = new Heap<Allocation>();
	var state = new ArrayList<Allocation>();
	var confirmed = new ArrayList<Integer>(reservations.size());

	// order by departure date
	reservations = reservations.stream().sorted().collect(Collectors.toList());
	for (Reservation reservation : reservations) {
		var allocation = findAllocation(reservation, state);
		if (allocation == null) {
			allocation = (new Rooms()).new Allocation(reservation.out, state.size());
			state.add(allocation);
		} else {
			allocation.out = reservation.out;
		}
		confirmed.add(allocation.room);
	}


	return confirmed.stream().map(room -> room + 1).collect(Collectors.toList());
}


// given a reservation, find and update allocation, or create a new one, this is brute force but will stop early if
// we find a delta of just 1 day
private static Allocation findAllocation(Reservation reservation, List<Allocation> state) {

	Allocation allocation = null;
	// brute force, look for the smallest delta, we stop if we find a 1
	if (!state.isEmpty()) {
		int delta = -1;
		int deltaIndex = -1;
		int i = 0;
		while (i<state.size() && delta!=1) {
			int currentDelta = reservation.in-state.get(i).out;
			if (delta==-1 ||(currentDelta>0 && currentDelta<delta)) {
				delta = currentDelta;
				deltaIndex = i;
			}
			i++;
		}
		if (delta>0) { // this is the minimum, if it's still negative it means there is no possible allocation
			allocation = state.get(deltaIndex);
		}
	}

	return allocation;
}

}

