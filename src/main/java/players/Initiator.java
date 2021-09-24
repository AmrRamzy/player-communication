package players;

import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * represents initiator player who will initiate the first message 
 * 
 * @author Amr Ramzy
 *
 */
public class Initiator extends Player {

	/**
	 * @param sentMessageQueue queue used to store sent messages
	 * @param receivedMessageQueue queue used to store received messages
	 * @param lock ReentrantLock used to lock a block of code for multithreaded execution
	 */
	public Initiator(Queue<String> sentMessageQueue,Queue<String> receivedMessageQueue, ReentrantLock lock) {
		super(sentMessageQueue, receivedMessageQueue, lock);
	}
	
	/**
	 * method is used to initialize player and send the first message
	 * @return True if sent message queue , received message queue and the lock are not null and initial message sent successfully 
	 */
	@Override
	protected boolean initPlayer() {
		
		log.info("[{}] started.", Thread.currentThread().getName());
		if(sentMessageQueue ==null || receivedMessageQueue ==null || lock == null) {
			return false;
		}
		lock.lock();
		try {
			StringBuilder receivedMessageBuilder = new StringBuilder();
			receivedMessageBuilder.append(numberOfSentMessages);
			sentMessageQueue.add(receivedMessageBuilder.toString());
			log.info("[{}] sent {}.", Thread.currentThread().getName(),receivedMessageBuilder);
			numberOfSentMessages++;
			
		} finally {
			lock.unlock();
		}

		return true;
	}

}
