package cat.calidos.doodles;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cat.calidos.doodles.Rooms.Allocation;
import cat.calidos.doodles.Rooms.Reservation;

public class RoomsTest {


@Test
public void hotelRoomsTest() {
	var reservations = new ArrayList<Reservation>();
	reservations.add((new Rooms()).new Reservation(5,6)); // user 0: room 1
	reservations.add((new Rooms()).new Reservation(1,2)); // user 1: room 1
	reservations.add((new Rooms()).new Reservation(3,4)); // user 2: room 1
	reservations.add((new Rooms()).new Reservation(0,6)); // user 3: room 2

	var expected = List.of(1,1,1,2);
	assertEquals(expected, Rooms.hotelRooms(reservations));


	reservations.clear();
	reservations.add((new Rooms()).new Reservation(0,1)); // user 0: room 1
	reservations.add((new Rooms()).new Reservation(0,1)); // user 1: room 2
	reservations.add((new Rooms()).new Reservation(0,1)); // user 2: room 3

	expected = List.of(1,2,3);
	assertEquals(expected, Rooms.hotelRooms(reservations));
}


}

/*
 *    Copyright 2024 Daniel Giribet
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
