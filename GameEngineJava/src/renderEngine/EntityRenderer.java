package renderEngine;

import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import entities.Entity;
import models.RawModel;
import models.TextureModel;
import shaders.StaticShader;
import texture.ModelTexture;
import toolbox.Maths;

public class EntityRenderer {
	StaticShader shader;
	
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Map<TextureModel, List<Entity>> entities) {
		for(TextureModel model : entities.keySet()) {
			prepareTextureModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity: batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			
			unbindTextureModel();
		}
	}
	
	private void prepareTextureModel(TextureModel model) {
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		ModelTexture texture = model.getTexture();
		shader.loadShineVariables(texture.getShineDumper(),texture.getRefectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
	}
	
	private void unbindTextureModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(),
				entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
