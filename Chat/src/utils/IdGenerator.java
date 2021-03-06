package utils;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

public class IdGenerator {
	private Set<String> idSet = new HashSet<>();

	public synchronized String next()
	{
		String newId = RandomStringUtils.random(8, "0123456789abcdef");
		while(idSet.contains(newId)){
			newId = RandomStringUtils.random(8, "0123456789abcdef");
		}
		idSet.add(newId);
	    return newId;
	}
}
