package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram{
	private static final String VERTEX_FILE = "src/Shaders/TerrainVertexShader";
	private static final String FRAGMENT_FILE = "src/Shaders/TerrainFragmentShader";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_reflectivity;
	private int location_shineDamper;
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColour = super.getUniformLocation("lightColour");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_shineDamper = super.getUniformLocation("shineDamper");
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
	}
	
	public void loadShineVariables(float damper, float reflectivity)
	{
		super.loadFloat(location_reflectivity, reflectivity);
		super.loadFloat(location_shineDamper, damper);
	}
}
