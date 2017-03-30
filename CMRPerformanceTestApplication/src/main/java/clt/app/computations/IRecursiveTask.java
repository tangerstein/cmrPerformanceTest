package clt.app.computations;

public interface IRecursiveTask {

	public long recursiveMethod(long n, long numberOfRuns);

	public void setDepth(long depth);

	public void addToTaskList();
}
