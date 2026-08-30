package ayre.tasks;

import java.util.List;

import ayre.TaskList;

public record LoadResult(TaskList tasks, List<String> warnings) {
    public String getWarningsAsString() {
        if (this.warnings.isEmpty()) {
            return "<<Main System: Mission Log Loaded Successfully\n";
        }
        StringBuilder warnString = new StringBuilder();
        for (int i = 1; i <= this.warnings.size(); i++) {
            warnString.append(this.warnings.get(i - 1)).append("\n");
        }
        return "<<Main System: Mission Log Load Failed>>\n" + warnString;
    }
}
