package cs1302.hw06;

import java.time.LocalDate;

/** Represents an Email message in the cs1302 mock
 *  eLC application.
 *
 *  @author Brad Barnes
 *  @version 1.1; Mar 2 2019
 */
public class Email {
    /** Email address of the recipient. */
    private String recipient;
    /** Email address of the sender. */
    private String sender;
    /** Date sent. */
    private LocalDate sendDate;
    /** Contents of the Email message. */
    private String contents;

    /**
     * Constructs a new {@code Email} object.  All parameters must be specified
     * and non-null. {@code java.lang.String} parameters must not be empty and
     * any {@code java.lang.String} parameter representing an email address
     * must be a valid email address.
     *
     * @throws NullPointerException if any argument is {@code null}.
     * @throws IllegalArgumentException if any of the {@code java.lang.String}
     * arguments are empty or if the email addresses are invalid.
     * @param recipient email address of the recipient.
     * @param sender email address of the sender.
     * @param sendDate {@code java.time.LocalDate} reference representing
     * the date the email was sent.
     * @param contents the message contents (message body).
     */
    public Email(String recipient, String sender,
                 LocalDate sendDate, String contents) {
        checkNull("Email Constructor", contents, sendDate,
                  recipient, sender);
        checkEmpty("Email Constructor", recipient, sender, contents);
        checkAddress("Email Constructor", recipient, sender);

        this.sender = sender;
        this.recipient = recipient;
        this.sendDate = sendDate;
        this.contents = contents;
    } // Email

    /**
     * Returns the date that the Email was originally sent.
     *
     * @return the {@code java.time.LocalDate} this {@code Email}
     * was sent.
     */
    public LocalDate getDateSent() {
        return sendDate;
    } // getSendDate

    /**
     * Returns the email address of the recipient as a
     * {@code java.lang.String}.
     * @return the email address of the recipient of this
     * {@code Email} object.
     */
    public String getRecipient() {
        return recipient;
    } // getRecipient

    /**
     * Returns the email address of the sender as a
     * {@code java.lang.String}.
     * @return the email address of the sender of this
     * {@code Email} object.
     */
    public String getSender() {
        return sender;
    } // getSender

    /**
     * Returns the contents of this {@code Email} as a
     * {@code java.lang.String}.
     * @return the contents of this {@code Email} object.
     */
    public String getContents() {
        return contents;
    } // getSender

    /**
     * Throws a NullPointerException if any values in the
     * varargs parameter, {@code o} are {@code null}. The
     * method performs no actions if all values in {@code o}
     * are non-null.
     *
     * @param method the name of the calling method.
     * @param o varargs parameter containing all objects to verify
     * @throws NullPointerException if any element of parameter
     * {@code o} is null.
     */
    private void checkNull(String method, Object ... o) {
        for (Object obj: o) {
            if (obj == null) {
                throw new NullPointerException(method +
                                               ": Null Argument Provided");
            } // if
        } // for
    } // checkNull

    /**
     * Throws an IllegalArgumentException if any {@code Strings}
     * in the varargs parameter, {@code s} are empty. The
     * method performs no actions if all values in {@code s}
     * are non-null.
     *
     * @param method the name of the calling method.
     * @param s varargs parameter containing all strings to verify
     * @throws IllegalArgumentException if any element of parameter
     * {@code s} is empty.
     */
    private void checkEmpty(String method, String ... s) {
        for (String str: s) {
            if (str.equals("")) {
                throw new IllegalArgumentException(method +
                                                   ": Empty String Provided");
            } // if
        } // for
    } // checkEmpty

    /**
     * Throws an IllegalArgumentException if any {@code Strings}
     * in the varargs parameter, {@code s} do not contain a
     * valid email address. This method considers an email address
     * valid if it contains the {@code @} symbol and a {@code .}
     * somewhere after the {@code @}. This method for checking
     * validity would not hold up in a real-world system. For more
     * information on accurately checking email validity, see
     * the link below.
     * The method performs no actions if all values in {@code s}
     * contain valid email addresses.
     *
     * @param method the name of the calling method.
     * @param s varargs parameter containing all strings to verify
     * @throws IllegalArgumentException if any element of parameter
     * {@code s} contains an invalid address.
     */
    private void checkAddress(String method, String ... s) {
        for (String str: s) {
            int index = str.indexOf("@");
            if ((index == -1) || !(str.substring(index).contains("."))) {
                throw new IllegalArgumentException(method +
                                                   ": Invalid Email Address");
            } // if
        } // for
    } // checkAddress
} // Email
