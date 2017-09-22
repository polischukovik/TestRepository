import java.util.HashSet;
import java.util.Set;

public class IdGenerator {
	private Set<String> idCounter = new HashSet<>();

	public long createID()
	{
		RandomStringUtils.random(8, "0123456789abcdef")
	    return idCounter.getAndIncrement();
	}
}
