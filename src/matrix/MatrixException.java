package matrix;

import java.io.Serial;

public class MatrixException extends RuntimeException {
	
	@Serial
	private static final long serialVersionUID = 1L;

	public MatrixException() {
	}

	public MatrixException(String msg) {
    	    super(msg);
	}

}
