package final_project;

public class MyException extends Exception {
	public MyException(String exceptionMess) {
		super(exceptionMess);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
