package clt.app.computations;

public interface ITask {

	public long execute(long n, long numberOfRuns);

	public void setDepth(long depth);

	public void addToTaskList();
}
