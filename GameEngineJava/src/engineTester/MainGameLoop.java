package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import Terrains.Terrain;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TextureModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import texture.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		RawModel model = OBJLoader.loadOBJModel("ChristmasTree", loader);
		TextureModel staticModel = new TextureModel(model, new ModelTexture(loader.loadTexture("ChristmasTexture")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDumper(10);
		texture.setRefectivity(1);
		float scale = 0.8f;
		Entity entity = new Entity(staticModel, new Vector3f(0, 0, -5), 0, 0, 0, scale);
		Camera camera = new Camera();
		
		Light sun = new Light(new Vector3f(3000, 5000, 2000), new Vector3f(1, 1, 1));
		Light objLight = new Light(new Vector3f(-3, 2, -5), new Vector3f(1, 1, 1));
		
		List<Entity> allTrees = new ArrayList<Entity>();
		int max = 15;
		int min = -15;
		int random;
		
		for(int i = 0; i < 2000; i++)
		{
			random = ThreadLocalRandom.current().nextInt(min, max + 1);
			float x = ThreadLocalRandom.current().nextInt(min, max + 1) * ThreadLocalRandom.current().nextInt(min, max + 1);
			float y = 0;
			random = ThreadLocalRandom.current().nextInt(min, max + 1);
			float z = -random - ThreadLocalRandom.current().nextInt(min, max + 200);
			allTrees.add(new Entity(staticModel, new Vector3f(x, y, z), 0f, 0f, 0f, 0.5f));
		}
		
		Terrain terrain = new Terrain(400, 100, loader, new ModelTexture(loader.loadTexture("terrainTexture")));
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			entity.increaceRotation(0, 0.3f, 0);
			camera.move();
			
			for(Entity item : allTrees) {
				renderer.processEntity(item);
			}
			
			renderer.processTerrain(terrain);
			renderer.processEntity(entity);
			renderer.render(sun, objLight, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}	
}
