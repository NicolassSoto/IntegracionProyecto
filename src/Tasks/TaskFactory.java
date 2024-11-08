package Tasks;

public class TaskFactory {

	public static ITask createTask(TaskType taskType) {
        switch (taskType) {
            case AGGREGATOR:
                return new Aggregator();
            case CONTEXT_ENRICHER:
                return new ContextEnricher();
            case CORRELATION_ID_SETTER:
                return new CorrelationIdSetter();
            case CORRELATOR:
                return new Correlator();
            case DISTRIBUTOR:
                return new Distributor();
            case MERGER:
                return new Merger();
            case REPLICATOR:
                return new Replicator();
            case SPLITTER:
                return new Splitter(null, null, null);
            case TRANSLATOR:
                return new Translator();
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }
}
