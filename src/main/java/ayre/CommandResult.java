package ayre;

import ayre.enums.AyreStatus;

public record CommandResult(String message, AyreStatus status) {}