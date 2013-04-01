package millstein.RunBitMan2;

public interface LevelPlugin {

	public void gameRenderMobs();

	public void gameRenderMovement();

	public void gameRenderObjects();

	public void gameRenderHitbox(final long pauseTime);

	public void lost();

	public void cycler();
}
