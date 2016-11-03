package polischukovik.ui;

public class Response {
	private int status;
	private String message;
	
	public Response(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public Response(){};
	
	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + "]";
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
