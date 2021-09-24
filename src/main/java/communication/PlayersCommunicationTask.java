package communication;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import players.Initiator;
import players.Player;

/**
 * application entry point
 * 
 * @author Amr Ramzy
 *
 */
public class PlayersCommunicationTask {

	/**
	 * timeout value for Executor in milliseconds
	 */
	private static final int TIME_OUT = 5000;
	private static final Logger log = LoggerFactory.getLogger(PlayersCommunicationTask.class);

	/**
	 * application main method,
	 * creates and start the players threads 
	 * 
	 * @param args arguments for application execution
	 */
	public static void main(String[] args) {

		log.info("application started successfully");
		Queue<String> sentMessageQueue = new LinkedList<>();
		Queue<String> receivedMessageQueue = new LinkedList<>();

		ReentrantLock lock = new ReentrantLock(true);

		Player initiator = new Initiator(sentMessageQueue,receivedMessageQueue, lock);
		Player secondPlayer = new Player(receivedMessageQueue, sentMessageQueue, lock);

		ExecutorService  executorService = Executors.newFixedThreadPool(2);

		executorService.execute(initiator);
		executorService.execute(secondPlayer);

		executorService.shutdown();
		try {
			//set timeout to make sure the application terminate properly
			if (executorService.awaitTermination(TIME_OUT, TimeUnit.MILLISECONDS)) {
				log.info("application ended successfully");
			} else {
				executorService.shutdownNow();
				log.info("application ended due to timeout");
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
			log.error("application ended due to timeout", e);
			Thread.currentThread().interrupt();
		}

	}
}
