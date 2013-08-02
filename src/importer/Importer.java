package importer;

import java.util.Vector;
import android.content.res.AssetManager;

public interface Importer 
{
	public void importFile(String fileName);
	public void importFile(AssetManager manager, String fileName);
	public Vector<ModelData> getModels();
	public Material getMaterial(String mtlName);
}
