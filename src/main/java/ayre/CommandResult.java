package ayre;

import ayre.enums.AyreStatus;

//public final class CommandResult {
//    private final String MESSAGE;
//    private final AyreStatus STATUS;
//
//    public CommandResult(String msg, AyreStatus status) {
//        this.MESSAGE = msg;
//        this.STATUS = status;
//    }
//
//    public String getMessage() {
//        return this.MESSAGE;
//    }
//
//    public AyreStatus getStatus() {
//        return this.STATUS;
//    }
//}
public record CommandResult(String message, AyreStatus status) {}