import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
	private AtomicLong idCounter = new AtomicLong();

	public long createID()
	{
	    return idCounter.getAndIncrement();
	}
}
