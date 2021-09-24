package players;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InitiatorTest {

	private Queue<String> sentQueue;
	private Queue<String> receivedQueue;
	private ReentrantLock lock;
	private Player player;

	@BeforeEach
	void init() {
		sentQueue = new LinkedList<String>();
		receivedQueue = new LinkedList<String>();
		lock = new ReentrantLock();
		player = new Initiator(sentQueue, receivedQueue, lock);

	}
	
	@Test
	@DisplayName("initPlayer should handling NullPointerExceptions")
	void test_initPlayer_null_values() {
		player = new Initiator(null, null,null);
		assertFalse(() -> player.initPlayer());
	}
	
	@Test
	@DisplayName("initPlayer should handling NullPointerExceptions")
	void test_initPlayer_send_first_message() {
		assertTrue(() -> player.initPlayer());
		assertEquals(1, player.numberOfSentMessages);
	}

}
