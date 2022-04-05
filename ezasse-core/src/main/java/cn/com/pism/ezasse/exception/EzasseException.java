package cn.com.pism.ezasse.exception;

import cn.com.pism.ezasse.enums.EzasseExceptionCode;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/05 下午 12:50
 * @since 0.0.1
 */
public class EzasseException extends RuntimeException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public EzasseException() {
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EzasseException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public EzasseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EzasseException(EzasseExceptionCode code) {
        super(code.getCode() + ":" + code.getMessage());
    }
}
