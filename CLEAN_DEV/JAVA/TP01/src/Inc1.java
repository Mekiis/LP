
public class Inc1 implements IIncrement{

	@Override
	public synchronized void increment() {
		Singleton.getInstance().increment(pas);
	}

}
