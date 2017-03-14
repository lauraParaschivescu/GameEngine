package texture;

public class ModelTexture {
	private int textureID;
	private float reflectivity = 0;
	private float shineDumper = 1;
	
	public ModelTexture(int id) {
		textureID = id;
	}
	
	public int getTextureID() {
		return textureID;
	}
	
	public float getRefectivity() {
		return reflectivity;
	}

	public void setRefectivity(float refectivity) {
		this.reflectivity = refectivity;
	}

	public float getShineDumper() {
		return shineDumper;
	}

	public void setShineDumper(float shineDumper) {
		this.shineDumper = shineDumper;
	}
}
