package com.hg.mylifetomorrow.exception;

import java.sql.SQLException;
import java.sql.SQLWarning;

import org.springframework.dao.DataAccessException;

public class HgException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public HgException() {
        super();
    }
    
    public HgException(Throwable cause) {
        super(cause);
    }
    
    public int getSQLErrorCode(){
    	final Throwable cause=getCause();

		if(cause instanceof DataAccessException){
	    	DataAccessException sqlException=(DataAccessException)cause;
			Throwable sqlError=sqlException.getRootCause();
			if (sqlError instanceof SQLException) {
				SQLException mysqlError=(SQLException)sqlError;
				return mysqlError.getErrorCode();
			} else if(sqlError instanceof SQLWarning){
				SQLWarning mysqlError=(SQLWarning)sqlError;
				return mysqlError.getErrorCode();
			}
		}
    	return 0;
    }

}
