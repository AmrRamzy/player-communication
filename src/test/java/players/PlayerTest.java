package players;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class PlayerTest {

	private static final int TEST_TIME_OUT = 3000;
	private Queue<String> sentQueue;
	private Queue<String> receivedQueue;
	private ReentrantLock lock;
	private Player player;

	@BeforeEach
	void init() {
		sentQueue = new LinkedList<String>();
		receivedQueue = new LinkedList<String>();
		lock = new ReentrantLock();
		player = new Player(sentQueue, receivedQueue, lock);

	}

	@Test
	@DisplayName("initPlayer should handling NullPointerExceptions")
	void test_initPlayer() {
		player = new Player(null, null, null);
		assertFalse(() -> player.initPlayer());
	}

	@Test
	@DisplayName("Player thread should be terminated if interrupted")
	@Timeout(value = TEST_TIME_OUT, unit = TimeUnit.MILLISECONDS)
	void test_thread_interrupt() {
		Thread testThread = new Thread(player);
		assertAll(() -> testThread.start());
		testThread.interrupt();
	}

	@Test()
	@Timeout(value = TEST_TIME_OUT, unit = TimeUnit.MILLISECONDS)
	@DisplayName("lock should be released after method execution")
	void test_player_lock_release() {
		receivedQueue.add("0");
		assertAll(() -> player.play());
		assertEquals(0, player.lock.getHoldCount());
	}

	@Test
	@DisplayName("number of sent messages should be 1")
	@Timeout(value = TEST_TIME_OUT, unit = TimeUnit.MILLISECONDS)
	void test_player_sent_messages() {
		receivedQueue.add("0");
		assertAll(() -> player.play());
		assertEquals(1, player.numberOfSentMessages);
	}

	@Test
	@DisplayName("number of received messages should be 1")
	@Timeout(value = TEST_TIME_OUT, unit = TimeUnit.MILLISECONDS)
	void test_player_received_messages() {
		receivedQueue.add("0");
		assertAll(() -> player.play());
		assertEquals(1, player.numberOfReceivedMessages);
	}
}
