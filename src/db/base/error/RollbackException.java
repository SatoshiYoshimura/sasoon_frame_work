package db.base.error;

import java.sql.SQLException;

public class RollbackException extends SQLException {

	public RollbackException() {
	}

	public RollbackException(String reason) {
		super(reason);
	}

	public RollbackException(Throwable cause) {
		super(cause);
	}

	public RollbackException(String reason, String SQLState) {
		super(reason, SQLState);
	}

	public RollbackException(String reason, Throwable cause) {
		super(reason, cause);
	}

	public RollbackException(String reason, String SQLState, int vendorCode) {
		super(reason, SQLState, vendorCode);
	}

	public RollbackException(String reason, String sqlState, Throwable cause) {
		super(reason, sqlState, cause);
	}

	public RollbackException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(reason, sqlState, vendorCode, cause);
	}

}
