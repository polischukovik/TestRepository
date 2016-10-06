package polischukovik.msformating.interfaces;

import java.io.IOException;
import java.io.OutputStream;

public interface DocumentFactory {
	public void write(OutputStream os) throws IOException;
}
