package ayre;

import ayre.enums.Command;

import java.util.List;

public record ParsedInput(Command command, List<String> args) {}